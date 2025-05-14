package com.clone.android.testvideo.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import androidx.viewpager2.widget.ViewPager2
import com.clone.android.testvideo.data.entities.VideoEntity
import com.clone.android.testvideo.databinding.FragmentHomeBinding
import com.clone.android.testvideo.ui.fragment.home.adapter.CustomVideoAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel.getVideo()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.dataResponse.observe(viewLifecycleOwner) { data ->

            binding.containerProgress.visibility = View.GONE

            if (data != null && data.isNotEmpty() && binding.viewPager.adapter == null) {

                setViewPagerWithVideoData(data)

            } else if (data != null && data.isNotEmpty() && data.size > homeViewModel.getAdapter()!!.itemCount) {

                updateViewPagerWithNewData(data)

            }

        }

    }

    private fun updateViewPagerWithNewData(data: List<VideoEntity>) {
        val list = homeViewModel.getAdapter()!!.getList()
        val existingIds = getExistingIds(list)

        val newList = data.filter { it.id !in existingIds }

        homeViewModel.getAdapter()!!.updateWithNewItems(newList.toMutableList())
    }

    private fun setViewPagerWithVideoData(data: List<VideoEntity>) {
        homeViewModel.setAdapter(context?.let { ctx ->
            CustomVideoAdapter(data.toMutableList(), ctx)
        })

        binding.viewPager.setOffscreenPageLimit(2)
        binding.viewPager.setAdapter(homeViewModel.getAdapter())

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            private var settled = false

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == SCROLL_STATE_DRAGGING) {
                    settled = false
                }
                if (state == SCROLL_STATE_SETTLING) {
                    settled = true
                }
                if (state == SCROLL_STATE_IDLE && !settled) {
                    binding.containerProgress.visibility = View.VISIBLE
                    homeViewModel.setPage()
                    homeViewModel.getVideo()

                }
            }
        })
    }

    private fun getExistingIds(list: List<VideoEntity>?): Set<Int> {
        return list?.map { it.id }?.toSet() ?: emptySet()
    }

    override fun onDestroyView() {
        homeViewModel.compositeDisposable.clear()
        homeViewModel.clearVideoData()

        super.onDestroyView()
    }


}