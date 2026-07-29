package com.douglasrondini.drive_20_android.data.remote.helper

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository(
    private val gson: Gson = Gson()
) {

    /**
     * Para requisições que RETORNAM dados (GET, POST com corpo de retorno).
     * Mapeia o DTO da API para a classe de Domínio.
     */
    protected suspend fun <DTO, DOMAIN> safeApiCall(
        apiCall: suspend () -> Response<DTO>,
        transform: (DTO) -> DOMAIN
    ): Result<DOMAIN> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(transform(body))
                } else {
                    Result.failure(Exception("A resposta do servidor veio vazia."))
                }
            } else {
                val errorMessage = parseErrorMessage(response.errorBody()?.string())
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Falha de conexão. Verifique sua internet ou se a API está online."))
        } catch (e: Exception) {
            Result.failure(Exception(e.localizedMessage ?: "Erro inesperado ao se comunicar com o servidor."))
        }
    }

    /**
     * Para requisições que NÃO retornam dados no corpo (ex: POST/PUT que retornam apenas 200/201/204).
     */
    protected suspend fun safeApiCallUnit(
        apiCall: suspend () -> Response<Unit>
    ): Result<Unit> {
        return safeApiCall(
            apiCall = apiCall,
            transform = { Unit }
        )
    }

    /**
     * Converte a String do errorBody no modelo ApiErrorResponse e extrai a mensagem.
     */
    private fun parseErrorMessage(errorBody: String?): String {
        if (errorBody.isNullOrBlank()) return "Ocorreu um erro inesperado no servidor."
        
        return try {
            val parsedError = gson.fromJson(errorBody, ApiErrorResponse::class.java)
            parsedError.error ?: parsedError.message ?: "Erro ao processar requisição."
        } catch (e: Exception) {
            "Não foi possível processar a resposta de erro do servidor."
        }
    }
}
