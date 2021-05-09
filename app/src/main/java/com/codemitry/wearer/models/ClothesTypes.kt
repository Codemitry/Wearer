package com.codemitry.wearer.models

import com.codemitry.wearer.R
import java.util.*

enum class ClothesTypesByWearingWay {
    OUTERWEAR, LIGHT_CLOTHES, UNDERWEAR, SHOES, ACCESSORIES
}

interface ClothesSubtype {
    val nameResource: Int
    val iconResource: Int
}

fun clothesTypeByName(clothesType: String): ClothesTypesByWearingWay {
    return ClothesTypesByWearingWay.valueOf(clothesType.toUpperCase(Locale.getDefault()))
}

fun clothesSubtypeByName(clothesTypeByWearingWay: ClothesTypesByWearingWay, subtype: String): ClothesSubtype {
    return when (clothesTypeByWearingWay) {
        ClothesTypesByWearingWay.OUTERWEAR -> OuterwearTypes.valueOf(subtype.toUpperCase(Locale.getDefault()))
        ClothesTypesByWearingWay.LIGHT_CLOTHES -> LightClothesTypes.valueOf(subtype.toUpperCase(Locale.getDefault()))
        ClothesTypesByWearingWay.UNDERWEAR -> UnderwearTypes.valueOf(subtype.toUpperCase(Locale.getDefault()))
        ClothesTypesByWearingWay.SHOES -> ShoesTypes.valueOf(subtype.toUpperCase(Locale.getDefault()))
        ClothesTypesByWearingWay.ACCESSORIES -> AccessoriesTypes.valueOf(subtype.toUpperCase(Locale.getDefault()))
    }
}

enum class OuterwearTypes(override val nameResource: Int, override val iconResource: Int) : ClothesSubtype {
    OVERCOAT(R.string.overcoat, R.drawable.overcoat),
    CLOAK(R.string.cloak, R.drawable.cloak),
    JACKET(R.string.jacket, R.drawable.jacket),
    PONCHO(R.string.poncho, R.drawable.poncho),
    WINDCHEATER(R.string.windcheater, R.drawable.windcheater),
}

enum class LightClothesTypes(override val nameResource: Int, override val iconResource: Int) : ClothesSubtype {
    HOODIE(R.string.hoodie, R.drawable.hoodie),
    JUMPER(R.string.jumper, R.drawable.jumper),
    SWEATER(R.string.sweater, R.drawable.sweater),
    VEST(R.string.vest, R.drawable.vest),
    SHIRT(R.string.shirt, R.drawable.shirt),
    T_SHIRT(R.string.tShirt, R.drawable.t_shirt),
    POLO(R.string.polo, R.drawable.polo),
    TOP(R.string.top, R.drawable.top),
    DRESS(R.string.dress, R.drawable.dress),

    TROUSERS(R.string.trousers, R.drawable.trousers),
    JEANS(R.string.jeans, R.drawable.jeans),
    KNICKERBOCKERS(R.string.knickerbockers, R.drawable.knickerbockers),
    SHORTS(R.string.shorts, R.drawable.shorts),
    SKIRT(R.string.skirt, R.drawable.skirt),
}

enum class UnderwearTypes(override val nameResource: Int, override val iconResource: Int) : ClothesSubtype {
    BRIEFS(R.string.briefs, R.drawable.briefs),
    BOXERS(R.string.boxers, R.drawable.boxers),
    NIGHTIE(R.string.nightie, R.drawable.nightie),
    SOCKS(R.string.socks, R.drawable.socks),
}

enum class ShoesTypes(override val nameResource: Int, override val iconResource: Int) : ClothesSubtype {
    SNICKERS(R.string.snickers, R.drawable.snickers),
    GUMSHOES(R.string.gumshoes, R.drawable.gumshoes),
    BOOT(R.string.boot, R.drawable.boot),
    HIGH_HEELS(R.string.highHeels, R.drawable.high_heels),
}

enum class AccessoriesTypes(override val nameResource: Int, override val iconResource: Int) : ClothesSubtype {
    SCARF(R.string.scarf, R.drawable.scarf),
    BAG(R.string.bag, R.drawable.bag),
    UMBRELLA(R.string.umbrella, R.drawable.umbrella),
}