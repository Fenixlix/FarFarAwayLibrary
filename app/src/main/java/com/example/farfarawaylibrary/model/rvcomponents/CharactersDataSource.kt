package com.example.farfarawaylibrary.model.rvcomponents

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.farfarawaylibrary.model.SwRepository
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.viewmodel.SwCharacterViewModel
import retrofit2.HttpException
import java.io.IOException

class CharactersDataSource (
    private val viewModel : SwCharacterViewModel
) : PagingSource<Int , SwCompleteCharacter>() {
    override fun getRefreshKey(state: PagingState<Int, SwCompleteCharacter>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SwCompleteCharacter> {
        return try {
            viewModel.progressBarEstate.value = true
            val pageNumber = params.key ?: 1
            val response = viewModel.repo.getCharacterByPage(pageNumber)

            var prevKey : Int? = null
            var nextKey : Int? = null

            viewModel.progressBarEstate.postValue(false)
            return if (response.isSuccessful){

                prevKey = getId(response.body()?.previous)
                nextKey = getId(response.body()?.next)

                LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = prevKey,
                    nextKey = nextKey,
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey,
                )
            }
        }catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    // Utility function to get the Id of a given url string
    private fun getId(urls : String?) : Int? {
        return if (!urls.isNullOrEmpty()){
            val segmentedUrl = urls.split("page=")
            segmentedUrl[1].toInt()
        } else {
            null
        }
    }
}