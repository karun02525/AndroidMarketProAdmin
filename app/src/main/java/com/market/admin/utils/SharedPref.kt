package com.market.admin.utils

import android.content.Context
import com.market.admin.App


class SharedPref private constructor() {

    companion object {
        val instance = SharedPref()


        private val preference_file_key = "com.demo.pusauli_preference"

        private val LANGUAGE = "language"
        private val IS_LOGGED_IN = "is_logged_in"
        private val IS_VIDEO_VIEW = "is_video"

        private val USER_ID = "user_id"
        private val VENDER_ID = "vender_id"
        private val CATEGORY = "category"

        private val USER_PASSWORD = "user_password"
        private val USER_NAME = "user_name"
        private val GENDER = "gender"
        private val CITY = "city"
        private val MOBILE_NUMBER = "mobile_number"
        private val MOBILE_NUMBER_OTHER = "other_mobile_number"
        private val SHOP_ADDRESS = "shop_address"
        private val SHOP_NEAR_BY_ADDRESS = "shop_neary_address"


        private val EMAIL_ID = "email_id"
        private val OWNER_EMAIL_ID = "owner_email_id"
        private val OWNER_MOBILE_NUMBER = "owner_mobile_number"
        private val OWNER_NAME = "owner_name"
        private val SHOP_NAME = "shop_name"
        private val SHOP_ADDRESS_PIN_CODE = "shop_pin_code"


        private val PROFILE_AVATAR = "profile_picture_url"
        private val SHOP_AVATAR = "shop_picture_url"
        private val AUTH_TOKEN = "auth_token"
        private val RefreshToken_TOKEN = "refreshToken"
        private val Employee_code = "Employee_code"
        private val Branch = "branch"
        private val Region = "region"
        private val Email = "email"
        private val Partners = "Partners"
        private val EarningPercentage = "earning_percentage"

    }


    private val sharedPref =
        App.appContext!!.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()


    var isLoginStatus: Int
        get() = sharedPref.getInt(IS_LOGGED_IN, 0)
        set(isLoginStatus) {
            editor.putInt(IS_LOGGED_IN, isLoginStatus)
            editor.apply()
        }


    var userId: Int?
        get() = sharedPref.getInt(USER_ID, 0)
        set(userId) {
            editor.putInt(USER_ID, userId!!)
            editor.apply()
        }

    var venderId: Int?
        get() = sharedPref.getInt(VENDER_ID, 0)
        set(venderId) {
            editor.putInt(VENDER_ID, venderId!!)
            editor.apply()
        }


    var employee_code: String?
        get() = sharedPref.getString(Employee_code, "")
        set(employee_code) {
            editor.putString(Employee_code, employee_code)
            editor.apply()
        }

    var category: String?
        get() = sharedPref.getString(CATEGORY, "")
        set(category) {
            editor.putString(CATEGORY, category)
            editor.apply()
        }

    var branch: String?
        get() = sharedPref.getString(Branch, "")
        set(branch) {
            editor.putString(Branch, branch)
            editor.apply()
        }

    var region: String?
        get() = sharedPref.getString(Region, "")
        set(region) {
            editor.putString(Region, region)
            editor.apply()
        }

    var email: String?
        get() = sharedPref.getString(Email, "")
        set(email) {
            editor.putString(Email, email)
            editor.apply()
        }

    var partners: String?
        get() = sharedPref.getString(Partners, "")
        set(partners) {
            editor.putString(Partners, partners)
            editor.apply()
        }

    var userName: String?
        get() = sharedPref.getString(USER_NAME, "")
        set(userName) {
            editor.putString(USER_NAME, userName)
            editor.apply()
        }

    var checkLanguage: String?
        get() = sharedPref.getString(LANGUAGE, "")
        set(language) {
            editor.putString(LANGUAGE, language)
            editor.apply()
        }

    var mobileNumber: String?
        get() = sharedPref.getString(MOBILE_NUMBER, "")
        set(mobile) {
            editor.putString(MOBILE_NUMBER, mobile)
            editor.apply()
        }

    var ownerMobileNumber: String?
        get() = sharedPref.getString(OWNER_MOBILE_NUMBER, "")
        set(mobile) {
            editor.putString(OWNER_MOBILE_NUMBER, mobile)
            editor.apply()
        }

    var mobileOtherNumber: String?
        get() = sharedPref.getString(MOBILE_NUMBER_OTHER, "")
        set(mobile) {
            editor.putString(MOBILE_NUMBER_OTHER, mobile)
            editor.apply()
        }

    var earningPercentage: String?
        get() = sharedPref.getString(EarningPercentage, "")
        set(earning_percentage) {
            editor.putString(EarningPercentage, earning_percentage)
            editor.apply()
        }

    var emailId: String?
        get() = sharedPref.getString(EMAIL_ID, "")
        set(emailId) {
            editor.putString(EMAIL_ID, emailId)
            editor.apply()
        }

    var ownerEmailId: String?
        get() = sharedPref.getString(OWNER_EMAIL_ID, "")
        set(emailId) {
            editor.putString(OWNER_EMAIL_ID, emailId)
            editor.apply()
        }

    var gender: String?
        get() = sharedPref.getString(GENDER, "")
        set(gender) {
            editor.putString(GENDER, gender)
            editor.apply()
        }
    var city: String?
        get() = sharedPref.getString(CITY, "")
        set(city) {
            editor.putString(CITY, city)
            editor.apply()
        }

    var profileAvatar: String?
        get() = sharedPref.getString(PROFILE_AVATAR, "")
        set(avatar) {
            editor.putString(PROFILE_AVATAR, avatar)
            editor.apply()
        }

    var shopAvatar: String?
        get() = sharedPref.getString(SHOP_AVATAR, "")
        set(shop_avatar) {
            editor.putString(SHOP_AVATAR, shop_avatar)
            editor.apply()
        }


    var shopAddress: String?
        get() = sharedPref.getString(SHOP_ADDRESS, "")
        set(address) {
            editor.putString(SHOP_ADDRESS, address)
            editor.apply()
        }

    var shopNearBy: String?
        get() = sharedPref.getString(SHOP_NEAR_BY_ADDRESS, "")
        set(address) {
            editor.putString(SHOP_NEAR_BY_ADDRESS, address)
            editor.apply()
        }
    var pinCode: String?
        get() = sharedPref.getString(SHOP_ADDRESS_PIN_CODE, "")
        set(pincode) {
            editor.putString(SHOP_ADDRESS_PIN_CODE, pincode)
            editor.apply()
        }

    var shopName: String?
        get() = sharedPref.getString(SHOP_NAME, "")
        set(name) {
            editor.putString(SHOP_NAME, name)
            editor.apply()
        }

    var owerName: String?
        get() = sharedPref.getString(OWNER_NAME, "")
        set(name) {
            editor.putString(OWNER_NAME, name)
            editor.apply()
        }


    var authToken: String?
        get() = sharedPref.getString(AUTH_TOKEN, "")
        set(authToken) {
            editor.putString(AUTH_TOKEN, authToken)
            editor.apply()
        }

    var refreshToken: String?
        get() = sharedPref.getString(RefreshToken_TOKEN, "")
        set(refreshToken) {
            editor.putString(RefreshToken_TOKEN, refreshToken)
            editor.apply()
        }


    var isVideoView: Boolean
        get() = sharedPref.getBoolean(IS_VIDEO_VIEW, false)
        set(isVideoView) {
            editor.putBoolean(IS_VIDEO_VIEW, isVideoView)
            editor.apply()
        }

    fun logOut() {
        val lang = SharedPref.instance.checkLanguage
        editor.clear().apply()
        SharedPref.instance.checkLanguage = lang!!

    }
}
