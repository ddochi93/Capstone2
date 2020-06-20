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

        if(Person.gender == 0)
            data.add("성별 : 남자")
        else
            data.add("성별 : 여자")
        data.add("나이 : ${Person.age}")
        data.add("키 : ${Person.height}cm")
        data.add("몸무게 : ${Person.weight}kg")
        when(Person.activity) {
            1 -> data.add("매우 활동적")
            2 -> data.add("활동적")
            3 -> data.add("저 활동적")
            4 -> data.add("비활동적")
        }


    }
}