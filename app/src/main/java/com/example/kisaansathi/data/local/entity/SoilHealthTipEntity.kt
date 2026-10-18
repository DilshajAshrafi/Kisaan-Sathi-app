package com.example.kisaansathi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soil_health_tips")
data class SoilHealthTipEntity(
    @PrimaryKey
    val id: String,
    val category: String, // nutrient, salinity, organic, ph, erosion, drainage
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val idealSoilType: String,
    val actionableStepsEn: String,
    val actionableStepsHi: String,
    val benefitMetric: String,
    val isCritical: Boolean = true,
    val cachedAtTimestamp: Long = System.currentTimeMillis()
)
