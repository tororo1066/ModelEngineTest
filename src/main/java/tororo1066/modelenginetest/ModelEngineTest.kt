package tororo1066.modelenginetest

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ModeledEntity
import tororo1066.tororopluginapi.SJavaPlugin

class ModelEngineTest: SJavaPlugin() {

    companion object{
        lateinit var modelApi: ModelEngineAPI
        val models = ArrayList<ModeledEntity>()
    }

    override fun onEnable() {
        modelApi = ModelEngineAPI.api
        MECommand()
    }
}