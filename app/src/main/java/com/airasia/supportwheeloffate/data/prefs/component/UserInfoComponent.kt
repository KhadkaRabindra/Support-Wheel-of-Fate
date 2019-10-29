package com.airasia.supportwheeloffate.data.prefs.component

import com.airasia.supportwheeloffate.data.prefs.entity.UserInfo
import com.airasia.supportwheeloffate.repository.LoginRepository
import com.airasia.supportwheeloffate.repository.UserRepository
import com.airasia.supportwheeloffate.service.HeaderInterceptor
import com.airasia.supportwheeloffate.utils.LoginUtils
import com.skydoves.preferenceroom.PreferenceComponent

@PreferenceComponent(entities = [(UserInfo::class)])
interface UserInfoComponent {
    fun inject(target: LoginRepository)

    fun inject(target: UserRepository)

    fun inject(target: HeaderInterceptor)

    fun inject(target: LoginUtils)

}