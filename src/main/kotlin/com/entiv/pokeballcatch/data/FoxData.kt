package com.entiv.pokeballcatch.data

import de.tr7zw.nbtapi.NBTCompound
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.AnimalTamer
import org.bukkit.entity.Fox

object FoxData : DataWrapper<Fox>(Fox::class) {
    override fun entityWriteToNbt(entity: Fox, compound: NBTCompound) {
        entity.firstTrustedPlayer?.name.let {
            compound.setString("firstTrustedPlayer", it)
        }
        entity.firstTrustedPlayer?.name.let {
            compound.setString("secondTrustedPlayer", it)
        }

        compound.setString("foxType", entity.foxType.name)
    }

    override fun nbtWriteToEntity(compound: NBTCompound, entity: Fox) {
        entity.firstTrustedPlayer = Bukkit.getPlayer(compound.getString("firstTrustedPlayer"))
        entity.secondTrustedPlayer = Bukkit.getPlayer(compound.getString("secondTrustedPlayer"))

        entity.foxType = Fox.Type.valueOf(compound.getString("foxType"))
    }

    override fun entityWriteToComponent(entity: Fox, components: MutableList<Component>) {

        addComponent(components, "品种", entity.foxType.translate())

        val firstTrustedPlayer = entity.firstTrustedPlayer
        val secondTrustedPlayer = entity.secondTrustedPlayer

        if (firstTrustedPlayer != null && secondTrustedPlayer != null) {
            addComponent(components, "信任玩家", "${firstTrustedPlayer.name}, ${secondTrustedPlayer.name}")
        } else if (firstTrustedPlayer == null && secondTrustedPlayer == null) {
            addComponent(components, "信任玩家", "无")
        } else {
            addComponent(components, "信任玩家", "${firstTrustedPlayer?.name ?: secondTrustedPlayer!!.name}")
        }
    }

    fun Fox.Type.translate(): String {
        return when (this) {
            Fox.Type.RED -> "红狐"
            Fox.Type.SNOW -> "北极狐"
        }
    }
}