package com.example.harusikdan.feature.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment(), MyPageContract.View {
    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MyPagePresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return myPageBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        myPageBinding = FragmentMyPageBinding.inflate(inflater, container, false)
    }

    private fun initView() {
        var data: ArrayList<String> = ArrayList()
        var adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, data)
        myPageBinding.list.adapter = adapter

        data.add("아이디 : ${Person.id}")
        if(Person.gender == 0)
            data.add("성별 : 남자")
        else
            data.add("성별 : 여자")
        data.add("나이 : ${Person.age}")
        data.add("키 : ${Person.height}cm")
        data.add("몸무게 : ${Person.weight}kg")
        when(Person.activity) {
            40 -> data.add("활동성 : 매우 활동적")
            33 -> data.add("활동성 : 활동적")
            25 -> data.add("활동성 : 저 활동적")
            20 -> data.add("활동성 : 비활동적")
        }

        var diseaseString = ""
        Person.diseaseList.map {
            when(it) {
                1 -> diseaseString += (" 신장질환 ")
                2 -> diseaseString += (" 비만 ")
                3 -> diseaseString += (" 고혈압 ")
                4 -> diseaseString += (" 당뇨 ")
                else -> true
            }
        }
        data.add("질병 목록 : $diseaseString")

    }
}