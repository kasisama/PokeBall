package com.entiv.pokeballcatch.data

import de.tr7zw.nbtapi.NBTCompound
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Enderman


object EndermanData : DataWrapper<Enderman>(Enderman::class) {
    override fun entityWriteToNbt(entity: Enderman, compound: NBTCompound) {

        compound.setString("carriedBlock", entity.carriedBlock?.material.toString())

        entity.carriedBlock?.material?.let {
            compound.setString("carriedBlock", it.toString())
        }
    }

    override fun nbtWriteToEntity(compound: NBTCompound, entity: Enderman) {
        val name = compound.getString("carriedBlock") ?: return
        val blockData = Material.valueOf(name).createBlockData()

        entity.carriedBlock = blockData
    }

    override fun entityWriteToComponent(entity: Enderman, components: MutableList<Component>) {
        addComponent(components, "持有方块", entity.carriedBlock?.material?.translationKey() ?: "无")
    }

}