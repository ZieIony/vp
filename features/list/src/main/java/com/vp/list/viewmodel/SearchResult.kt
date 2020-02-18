package com.vp.list.viewmodel

import com.vp.list.model.ListItem
import java.util.*

sealed class SearchResult(val items: List<ListItem>, val totalResult: Int, val listState: ListState) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as SearchResult
        return totalResult == that.totalResult &&
                items == that.items && listState == that.listState
    }

    override fun hashCode(): Int {
        return Objects.hash(items, totalResult, listState)
    }

    object Error : SearchResult(emptyList(), 0, ListState.ERROR)
    class Success(items: List<ListItem>, totalResult: Int) : SearchResult(items, totalResult, ListState.LOADED)
    object InProgress : SearchResult(emptyList(), 0, ListState.IN_PROGRESS)

}