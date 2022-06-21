package tororo1066.modelenginetest

import com.ticxo.modelengine.api.util.ConfigManager
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import tororo1066.tororopluginapi.sCommand.SCommand
import tororo1066.tororopluginapi.sCommand.SCommandArg
import tororo1066.tororopluginapi.sCommand.SCommandObject
import tororo1066.tororopluginapi.sCommand.SCommandOnlyPlayerData
import java.util.function.Consumer

class MECommand: SCommand("metest") {

    init {
        addCommand(SCommandObject().addArg(SCommandArg().addAllowString("model")).addArg(SCommandArg().addAllowString(EntityType.values().map { it.toString() }.toTypedArray())).addArg(SCommandArg().addAllowString(ModelEngineTest.modelApi.modelManager.modelRegistry.registeredModel.keys.toTypedArray())).setExecutor(
            Consumer<SCommandOnlyPlayerData> {
                it.sender.world.spawn(it.sender.location,EntityType.valueOf(it.args[1]).entityClass!!) { entity ->
                    entity.isSilent = true
                    if (entity is LivingEntity){
                        entity.isInvisible = true
                    }
                    val modelEntity = ModelEngineTest.modelApi.modelManager.createModeledEntity(entity)
                    val model = ModelEngineTest.modelApi.modelManager.createActiveModel(it.args[2])
                    model.animationMode = ConfigManager.AnimationMode.A
                    modelEntity.addActiveModel(model)
                    modelEntity.detectPlayers()
                    modelEntity.setStepHeight(0.5)
                    ModelEngineTest.models.add(modelEntity)
                }
            }
        ))

        addCommand(SCommandObject().addArg(SCommandArg().addAllowString("anim")).addArg(SCommandArg().addAllowString(ModelEngineTest.modelApi.modelManager.modelRegistry.registeredModel.keys.toTypedArray())).addArg(SCommandArg().addAlias("animation name")).setExecutor(
            Consumer<SCommandOnlyPlayerData> { com ->
                val model = ModelEngineTest.models.filter { it.allActiveModel.keys.contains(com.args[1]) }
                if (model.isEmpty()){
                    com.sender.sendMessage("モデルがいません")
                    return@Consumer
                }
                model.forEach {
                    it.getActiveModel(com.args[1]).addState(com.args[2],0,1,1.0)
                }
            }
        ))
    }
}