package dev.loqo71la.schoolmanagement.module.common.controller;

import java.util.List;

/**
 * Wraps a generic http response for collections.
 *
 * @param totalItem   total number of models.
 * @param totalPage   total number of pages.
 * @param currentPage current page requested;
 * @param items       list of model requested.
 * @param <T>         Generic type of wrapper.
 */
public record ResultPage<T>(
        int totalItem,
        int totalPage,
        int currentPage,
        List<T> items
) {
}
