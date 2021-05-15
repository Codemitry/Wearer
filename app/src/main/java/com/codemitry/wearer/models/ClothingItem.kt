package com.codemitry.wearer.models

import com.codemitry.wearer.R
import java.io.Serializable

data class ClothingItem(
    val name: String,
    val brand: String? = null,
    val size: String? = null,
    val purchaseYear: Int? = null,
    val material: String? = null,
    val notes: String? = null,
    val caringLabels: List<CaringLabels>?,
    val photoUrl: String?
) : Serializable


enum class CaringLabels(val nameResource: Int, val iconResource: Int) {
    BLEACHING_OK(R.string.bleaching, R.drawable.wh_bleaching_ok),
    BLEACHING_NO(R.string.bleaching_no, R.drawable.wh_bleaching_no),
    BLEACHING_NON_CHLORINE(R.string.bleaching_non_chlorine, R.drawable.wh_bleaching_non_chlorine),


    MACHINE_WASHING(R.string.machine_washing, R.drawable.wh_machine_washing),

    WASHING_30DEG(R.string.washing_30deg, R.drawable.wh_washing_30deg),
    WASHING_40DEG(R.string.washing_40deg, R.drawable.wh_washing_40deg),
    WASHING_50DEG(R.string.washing_50deg, R.drawable.wh_washing_50deg),
    WASHING_60DEG(R.string.washing_60deg, R.drawable.wh_washing_60deg),
    WASHING_70DEG(R.string.washing_70deg, R.drawable.wh_washing_70deg),
    WASHING_95DEG(R.string.washing_95deg, R.drawable.wh_washing_95deg),

    WASHING_30DEG_PERM_PRESS(R.string.washing_30deg_permanent_press, R.drawable.wh_washing_30deg_permanent_press),
    WASHING_40DEG_PERM_PRESS(R.string.washing_40deg_permanent_press, R.drawable.wh_washing_40deg_permanent_press),
    WASHING_50DEG_PERM_PRESS(R.string.washing_50deg_permanent_press, R.drawable.wh_washing_50deg_permanent_press),
    WASHING_60DEG_PERM_PRESS(R.string.washing_60deg_permanent_press, R.drawable.wh_washing_60deg_permanent_press),
    WASHING_95DEG_PERM_PRESS(R.string.washing_95deg_permanent_press, R.drawable.wh_washing_95deg_permanent_press),

    WASHING_30DEG_PERM_PRESS_ALT(R.string.washing_30deg_permanent_press, R.drawable.wh_washing_30deg_permanent_press_alt),
    WASHING_40DEG_PERM_PRESS_ALT(R.string.washing_40deg_permanent_press, R.drawable.wh_washing_40deg_permanent_press_alt),
    WASHING_50DEG_PERM_PRESS_ALT(R.string.washing_50deg_permanent_press, R.drawable.wh_washing_50deg_permanent_press_alt),
    WASHING_60DEG_PERM_PRESS_ALT(R.string.washing_60deg_permanent_press, R.drawable.wh_washing_60deg_permanent_press_alt),
    WASHING_95DEG_PERM_PRESS_ALT(R.string.washing_95deg_permanent_press, R.drawable.wh_washing_95deg_permanent_press_alt),

    WASHING_30DEG_EXTRA_CARE(R.string.washing_30deg_extra_care, R.drawable.wh_washing_30deg_extra_care),
    WASHING_40DEG_EXTRA_CARE(R.string.washing_40deg_extra_care, R.drawable.wh_washing_40deg_extra_care),

    WASHING_30DEG_EXTRA_CARE_ALT(R.string.washing_30deg_extra_care, R.drawable.wh_washing_30deg_extra_care_alt),
    WASHING_40DEG_EXTRA_CARE_ALT(R.string.washing_40deg_extra_care, R.drawable.wh_washing_40deg_extra_care_alt),

    WASHING_30DEG_ALT(R.string.washing_30deg, R.drawable.wh_washing_30deg_alt),
    WASHING_40DEG_ALT(R.string.washing_40deg, R.drawable.wh_washing_40deg_alt),
    WASHING_50DEG_ALT(R.string.washing_50deg, R.drawable.wh_washing_50deg_alt),
    WASHING_60DEG_ALT(R.string.washing_60deg, R.drawable.wh_washing_60deg_alt),
    WASHING_70DEG_ALT(R.string.washing_70deg, R.drawable.wh_washing_70deg_alt),
    WASHING_95DEG_ALT(R.string.washing_95deg, R.drawable.wh_washing_95deg_alt),

    WASHING_NO(R.string.washing_no, R.drawable.wh_washing_no),

    HAND_WASHING(R.string.hand_washing, R.drawable.wh_hand_washing),
    HAND_WASHING_COLD(R.string.hand_washing_cold, R.drawable.wh_washing_hand_cold),
    HAND_WASHING_WARM(R.string.hand_washing_warm, R.drawable.wh_washing_hand_warm),

    IRONING(R.string.ironing, R.drawable.wh_ironing),
    IRONING_NO(R.string.ironing_no, R.drawable.wh_ironing_no),
    IRONING_STEAM_NO(R.string.ironing_steam_no, R.drawable.wh_ironing_steam_no),
    IRONING_COOL(R.string.ironing_cool, R.drawable.wh_ironing_cool),
    IRONING_WARM(R.string.ironing_warm, R.drawable.wh_ironing_warm),
    IRONING_HOT(R.string.ironing_hot, R.drawable.wh_ironing_hot),


}