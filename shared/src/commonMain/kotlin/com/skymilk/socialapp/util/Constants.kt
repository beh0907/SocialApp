package com.skymilk.socialapp.util

object Constants {

    //API 서버 URL
    const val BASE_URL = "http://192.168.0.10:8888/"

    //DataStore 파일명
    const val PREFERENCE_FILE_NAME = "user_settings.preferences_pb"

    //파라미터 아이디
    const val USER_ID_PARAMETER = "userId"
    const val CURRENT_USER_ID_PARAMETER = "currentUserId"
    const val PAGE_QUERY_PARAMETER = "page"
    const val PAGE_SIZE_QUERY_PARAMETER = "limit"

    //네트워크 에러 메시지
    const val NO_INTERNET_ERROR_MESSAGE = "네트워크 연결 상태를 확인해주세요."
    const val UNEXPECTED_ERROR = "예상치 못한 오류가 발생했습니다. 다시 시도해 주세요!"

    // API 페이징
    const val FIRST_PAGE_INDEX = 1
    const val PAGE_SIZE = 20
}