package com.example.squadmaker.model.remotedatasource.retrofit.api

import com.example.squadmaker.BuildConfig
import com.example.squadmaker.model.remotedatasource.retrofit.characterresponse.CharactersResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.squadmaker.model.remotedatasource.retrofit.comicsresponse.ComicsResponseDTO as ComicResponse

/**
 * An interface representing all the queries that the Application is using to interact
 * with the Marvel API.
 */
interface MarvelApiService {

    /**
     * Queries a list of characters from the Marvel API.
     *
     * @param orderBy the required field to order the results.
     * @param limit the limit of the returned results.
     * @param ts a [String] with the current timestamp.
     * @param apiKey a [String] with the public Api key used for the query.
     * @param hash a [String] with the Md5 hash used for security reasons
     */
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: String = "40",
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String = BuildConfig.PUBLIC_API_KEY,
        @Query("hash") hash: String
    ): CharactersResponseDTO

    /**
     * Queries a specific character's details.
     *
     * @param characterId an [Int] representing the id of the character.
     * @param ts a [String] with the current timestamp.
     * @param apiKey a [String] with the public Api key used for the query.
     * @param hash a [String] with the Md5 hash used for security reasons
     */
    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String = BuildConfig.PUBLIC_API_KEY,
        @Query("hash") hash: String
    ): CharactersResponseDTO

    /**
     * Queries the comics of a specific character.
     *
     * @param format the format of the request.
     * @param formatType the format type of the request.
     * @param characterId an [Int] with the character's id
     * @param ts a [String] with the current timestamp.
     * @param apiKey a [String] with the public Api key used for the query.
     * @param hash a [String] with the Md5 hash used for security reasons
     */
    @GET("/v1/public/comics")
    suspend fun getComicsByCharacterId(
        @Query("format") format: String = "comic",
        @Query("formatType") formatType: String = "comic",
        @Query("characters") characterId: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String = BuildConfig.PUBLIC_API_KEY,
        @Query("hash") hash: String
    ): ComicResponse
}