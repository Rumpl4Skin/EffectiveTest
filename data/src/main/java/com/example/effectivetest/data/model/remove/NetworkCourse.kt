package com.example.effectivetest.data.model.remove

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Serializable
data class NetworkCourse(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("cover")
    val cover: String? = null,

    @SerialName("acquired_skills")
    val acquiredSkills: List<String>? = null,
    @SerialName("certificate")
    val certificate: String? = null,
    @SerialName("requirements")
    val requirements: String? = null,
    @SerialName("summary")
    val summary: String? = null,
    @SerialName("workload")
    val workload: String? = null,
    @SerialName("intro")
    val intro: String? = null,
    @SerialName("language")
    val language: String? = null,

    @SerialName("announcements")
    val announcements: List<Long>? = null,
    @SerialName("authors")
    val authors: List<Long>? = null,
    @SerialName("instructors")
    val instructors: List<Long>? = null,
    @SerialName("sections")
    val sections: List<Long>? = null,
    @SerialName("preview_lesson")
    val previewLesson: Long = 0,
    @SerialName("preview_unit")
    val previewUnit: Long = 0,

    @SerialName("course_format")
    val courseFormat: String? = null,
    @SerialName("target_audience")
    val targetAudience: String? = null,
    @SerialName("certificate_footer")
    val certificateFooter: String? = null,
    @SerialName("certificate_cover_org")
    val certificateCoverOrg: String? = null,

    @SerialName("total_units")
    val totalUnits: Long = 0,

    @SerialName("enrollment")
    var enrollment: Long = 0,
    @SerialName("owner")
    val owner: Long = 0,

    @SerialName("readiness")
    val readiness: Double = 0.0,

    @SerialName("is_contest")
    val isContest: Boolean = false,
    @SerialName("is_featured")
    val isFeatured: Boolean = false,
    @SerialName("is_active")
    val isActive: Boolean = false,
    @SerialName("is_public")
    val isPublic: Boolean = false,
    @SerialName("is_archived")
    val isArchived: Boolean = false,
    @SerialName("is_favorite")
    var isFavorite: Boolean = false,
    @SerialName("is_proctored")
    val isProctored: Boolean = false,
    @SerialName("is_in_wishlist")
    val isInWishlist: Boolean = false,
    @SerialName("is_enabled")
    val isEnabled: Boolean = false,

    @SerialName("certificate_distinction_threshold")
    val certificateDistinctionThreshold: Long = 0,
    @SerialName("certificate_regular_threshold")
    val certificateRegularThreshold: Long = 0,
    @SerialName("certificate_link")
    val certificateLink: String? = null,
    @SerialName("is_certificate_auto_issued")
    val isCertificateAutoIssued: Boolean = false,
    @SerialName("is_certificate_issued")
    val isCertificateIssued: Boolean = false,
    @SerialName("with_certificate")
    val withCertificate: Boolean = false,

    @SerialName("last_deadline")
    val lastDeadline: String? = null,
    @SerialName("begin_date")
    val beginDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,

    @SerialName("slug")
    val slug: String? = null,

    @SerialName("schedule_link")
    val scheduleLink: String? = null,
    @SerialName("schedule_long_link")
    val scheduleLongLink: String? = null,
    @SerialName("schedule_type")
    val scheduleType: String? = null,

    @SerialName("last_step")
    val lastStepId: String? = null,
    @SerialName("learners_count")
    val learnersCount: Long = 0,
    @SerialName("review_summary")
    val reviewSummary: Long = 0,

    @SerialName("time_to_complete")
    val timeToComplete: Long? = null,

    @SerialName("update_date")
    val updateDate: String? = null,
    @SerialName("canonical_url")
    val canonicalUrl: String? = null,

    @SerialName("is_popular")
    val isPopular: Boolean = false,
    @SerialName("difficulty")
    val difficulty: String? = null,
    @SerialName("lessons_count")
    val lessonsCount: Long? = null,


    /*@SerialName("options")
    val courseOptions: CourseOptions? = null,

    @SerialName("actions")
    val actions: CourseActions? = null,*/

    /**
     * Paid courses fields
     */
    @SerialName("is_paid")
    val isPaid: Boolean = false,
    @SerialName("price")
    val price: String? = null,
    @SerialName("currency_code")
    val currencyCode: String? = null,
    @SerialName("display_price")
    val displayPrice: String? = null,
    @SerialName("price_tier")
    val priceTier: String? = null,
    @SerialName("default_promo_code_name")
    val defaultPromoCodeName: String? = null,
    @SerialName("default_promo_code_price")
    val defaultPromoCodePrice: String? = null,
    @SerialName("default_promo_code_discount")
    val defaultPromoCodeDiscount: String? = null,
    /*@SerialName("default_promo_code_expire_date")
    val defaultPromoCodeExpireDate: Date? = null*/
) {

    fun getNormalDateUpdate(): String {
        // Парсинг строки даты
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
            val zonedDateTime = ZonedDateTime.parse(
                this.updateDate,
                inputFormatter
            ) // Извлечение нужных компонентов даты
            val day = zonedDateTime.dayOfMonth
            val month = zonedDateTime.month.getDisplayName(TextStyle.FULL, Locale("ru"))
            val year = zonedDateTime.year

            return "$day $month $year"
        } else {
            return this.updateDate.toString()
        }

    }

    fun calculateRating(): Double {
        // Базовый рейтинг с учетом рабочей нагрузки и количества уроков
        val workload: Double = this.workload?.toDoubleOrNull() ?: 0.0
        var rating = (workload * 0.1) + (this.lessonsCount?.times(0.1) ?: 0.0)
        // Добавление баллов за популярность и активность
        if (this.isPopular) rating += 1.0
        if (this.isActive) rating += 1.0 // Учитываем количество учащихся (чем больше, тем выше рейтинг)
        rating += (this.learnersCount * 0.0001) // Учет сложности (простой = 0.5, средний = 1.0, сложный = 1.5)
        rating += when (this.difficulty) {
            "easy" -> 0.5
            "medium" -> 1.0
            "hard" -> 1.5
            else -> 0.0
        } // Ограничиваем максимальный рейтинг до 5.0
        return rating.coerceAtMost(5.0)
    }
}