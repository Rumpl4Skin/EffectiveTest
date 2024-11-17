package com.example.effetivetest.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    
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
    val isFavorite: Boolean = false,
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
)
