package com.airasia.supportwheeloffate.ui.home

import com.airasia.supportwheeloffate.R
import com.airasia.supportwheeloffate.core.BaseFragment
import com.airasia.supportwheeloffate.data.local.HomeItem
import com.airasia.supportwheeloffate.data.local.Issue
import com.airasia.supportwheeloffate.ui.home.adapter.HomeAdapter

class HomeFragment : BaseFragment<HomeFragmentViewModel,
        com.airasia.supportwheeloffate.databinding.FragmentHomeBinding>(HomeFragmentViewModel::class.java) {

    companion object {
        fun newInstance() : HomeFragment{
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel(viewModel: HomeFragmentViewModel) {
        mBinding.viewModel = viewModel
    }

    override fun initView() {
        super.initView()
        viewModel.setHomeData(getData())
        mBinding.recyclerView.adapter = HomeAdapter{

        }

    }

    fun getData() : ArrayList<HomeItem>{
        val mainData = ArrayList<HomeItem>()

        val issue1 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")
        val issue2 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")
        val issue3 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")
        val issue4 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")
        val issue5 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")
        val issue6 = Issue("How to merge contents of SQLite 3.7 WAL file into main database file", "0", "1", "1 min before")

        /*Featured*/
        val featuredIssue = ArrayList<Issue>()
        featuredIssue.add(issue1)
        featuredIssue.add(issue2)
        featuredIssue.add(issue3)
        featuredIssue.add(issue4)
        featuredIssue.add(issue5)
        featuredIssue.add(issue6)
        val featuredIssues = HomeItem("Popular Issues", featuredIssue)

        /*Latest*/
        val latestIssueList = ArrayList<Issue>()
        latestIssueList.add(issue1)
        latestIssueList.add(issue2)
        latestIssueList.add(issue3)
        latestIssueList.add(issue4)
        latestIssueList.add(issue5)
        latestIssueList.add(issue6)
        val homeItemLatestIssue = HomeItem("Popular Issues", latestIssueList)

        /*Popular*/
        val popularIssueList = ArrayList<Issue>()

        popularIssueList.add(issue1)
        popularIssueList.add(issue2)
        popularIssueList.add(issue3)
        popularIssueList.add(issue4)
        popularIssueList.add(issue5)
        popularIssueList.add(issue6)
        val homeItemPopularIssue = HomeItem("Latest Issues", latestIssueList)

        mainData.add(featuredIssues)
        mainData.add(homeItemLatestIssue)
        mainData.add(homeItemPopularIssue)

        return mainData
    }

}