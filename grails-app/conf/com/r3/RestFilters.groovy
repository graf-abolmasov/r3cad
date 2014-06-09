package com.r3

class RestFilters {

    private static String RANGE_HEADER = 'Range'

    def filters = {
        all(uri: '/**') {
            before = {
                def range = request.getHeader(RANGE_HEADER)
                if (range?.matches('items=\\d+-\\d')) {
                    range = range.replaceAll('items=', '').split('-')
                    params.start = range[0]
                    params.max = range[1]-range[0]
                }
                return true
            }
        }
    }
}
