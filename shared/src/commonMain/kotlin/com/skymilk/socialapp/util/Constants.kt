package com.skymilk.socialapp.util

object Constants {

    //API 서버 URL
    const val BASE_URL = "http://inhyeop.iptime.org:88/"

    //게시글 이미지 저장 경로
    const val POST_IMAGES_FOLDER = "post_images/"

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

    //유효성 검사 메시지
    const val INVALID_INPUT_NAME_MESSAGE = "2 ~ 20자리의 이름을 입력해주세요."
    const val INVALID_INPUT_BIO_MESSAGE = "상태 메시지는 150자를 초과할 수 없습니다."
    const val INVALID_INPUT_EMAIL_MESSAGE = "이메일 형식이 올바르지 않습니다."
    const val INVALID_INPUT_PASSWORD_MESSAGE = "비밀번호는 8자리 이상으로 입력해주세요."
    const val INVALID_INPUT_PASSWORD_CONFIRM_MESSAGE = "비밀번호가 일치하지 않습니다."
    const val INVALID_INPUT_CAPTION_MESSAGE = "게시글 내용을 입력해주세요."
    const val INVALID_UPLOAD_POST_IMAGE_MESSAGE = "이미지를 업로드해주세요."

    //표현 레이어 메시지
    const val READING_IMAGE_BYTES_ERROR_MESSAGE = "이미지를 읽을 수 없습니다."


    // API 페이징
    const val FIRST_PAGE_INDEX = 1
    const val PAGE_SIZE = 20

    //이벤트 버퍼 사이즈
    const val EVENT_BUS_BUFFER_CAPACITY = 5
}