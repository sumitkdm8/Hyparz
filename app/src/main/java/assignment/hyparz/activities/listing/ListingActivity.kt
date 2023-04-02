package assignment.hyparz.activities.listing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.hyparz.R
import assignment.hyparz.activities.adapters.ListingAdapter
import assignment.hyparz.activities.adapters.RecyclerViewClickListener
import assignment.hyparz.activities.detail.DetailActivity
import assignment.hyparz.databinding.ActivityListingBinding

class ListingActivity : AppCompatActivity(), RecyclerViewClickListener {
    private lateinit var binding: ActivityListingBinding
    private lateinit var viewModel: ListingViewModel
    private lateinit var adapter: ListingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_listing)
        viewModel = ViewModelProvider(this)[ListingViewModel::class.java]
        val rvListing = binding.rvListing
        adapter = ListingAdapter(this, this)
        rvListing.adapter = adapter
        rvListing.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.getListing("50").observe(this) {
            adapter.adds(it)
        }
    }

    override fun recyclerViewListClicked(v: View?, position: Int) {
        val result = adapter.getItemByPosition(position)
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("details", result)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}