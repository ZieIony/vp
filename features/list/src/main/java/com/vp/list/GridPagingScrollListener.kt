package com.vp.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


typealias LoadMoreItemsListener = (Int) -> Unit

class GridPagingScrollListener internal constructor(private val layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {
    private var loadMoreItemsListener = EMPTY_LISTENER
    private var isLastPage = false
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (shouldLoadNextPage()) {
            loadMoreItemsListener(nextPageNumber)
        }
    }

    private fun shouldLoadNextPage() =
            isNotLoadingInProgress && userScrollsToNextPage() && isNotFirstPage && hasNextPage()

    private fun userScrollsToNextPage() =
            layoutManager.childCount + layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount

    private val isNotFirstPage: Boolean
        private get() = layoutManager.findFirstVisibleItemPosition() >= 0 && layoutManager.itemCount >= PAGE_SIZE

    private fun hasNextPage() = !isLastPage

    private val isNotLoadingInProgress: Boolean
        private get() = !isLoading

    private val nextPageNumber: Int
        private get() = layoutManager.itemCount / PAGE_SIZE + 1

    fun setLoadMoreItemsListener(loadMoreItemsListener: LoadMoreItemsListener?) {
        this.loadMoreItemsListener = loadMoreItemsListener ?: EMPTY_LISTENER
    }

    fun markLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun markLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
    }

    companion object {
        private const val PAGE_SIZE = 10
        private val EMPTY_LISTENER: LoadMoreItemsListener = { }
    }

}