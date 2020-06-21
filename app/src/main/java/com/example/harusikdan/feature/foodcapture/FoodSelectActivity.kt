package com.example.harusikdan.feature.foodcapture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.entity.FoodVO
import com.example.harusikdan.databinding.ActivityFoodSelectBinding
import com.example.harusikdan.globalconst.Consts
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.koin.android.ext.android.get


class FoodSelectActivity : AppCompatActivity(), FoodSelectContract.View {
    private lateinit var foodSelectBinding: ActivityFoodSelectBinding
    private lateinit var presenter: FoodSelectPresenter
    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
        setRadioButtonListener()
    }

    private fun initPresenter() {
        presenter = FoodSelectPresenter(this, get())
    }

    private fun setUpDataBinding() {
        foodSelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_food_select)
        foodSelectBinding.activity = this
    }


    private fun initView() {
        val foodNameList = intent.getStringArrayListExtra(Consts.SELECT_FOOD_LIST)
        foodSelectBinding.first.text = foodNameList[0]
        foodSelectBinding.second.text = foodNameList[1]
        foodSelectBinding.third.text = foodNameList[2]
        foodSelectBinding.fourth.text = foodNameList[3]
    }

    private fun setRadioButtonListener() {
        //일번음식
        foodSelectBinding.first.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
                insertFoodToDb()
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }

        }

        //이번음식
        foodSelectBinding.second.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
                insertFoodToDb()
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }

        //삼번음식
        foodSelectBinding.third.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
                insertFoodToDb()
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }

        //사번음식
        foodSelectBinding.fourth.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
                insertFoodToDb()
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }
    }

    private fun insertFoodToDb() {
        realm.beginTransaction()

        val food = realm.createObject<FoodVO>(nextId())
        food.foodName = Food.foodName.toString()
        food.date = Food.date.toString()
        food.mealTime = Food.mealTime.toString()

        realm.commitTransaction()
        this.toastShort("id = ${food.id}  : ${food.foodName}, ${food.date} , ${food.mealTime}")
        AlertDialog.Builder(this)
            .setTitle("내용이 추가되었습니다.")
            .setPositiveButton("확인") { _, _ -> finish()}
            .show()
    }

    private fun nextId(): Int {
        val maxId = realm.where<FoodVO>().max("id")
        if(maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}