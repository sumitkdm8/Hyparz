package assignment.hyparz.activities.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import assignment.hyparz.R
import assignment.hyparz.dataRepository.backend.model.Result
import assignment.hyparz.databinding.ItemListingBinding

class ListingAdapter(private val mActivity: Context, itemClickListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<ListingAdapter.ListingViewVH>() {
    private var items: MutableList<Result> = mutableListOf()
    private val itemListener: RecyclerViewClickListener


    init {
        itemListener = itemClickListener
    }


    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingViewVH {
        return ListingViewVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(mActivity),
                R.layout.item_listing,
                null,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListingViewVH, position: Int) {
        val data: Result = items[position]
        holder.bindData(data)
    }

    fun adds(resultList: List<Result>) {
        if (resultList != null) {
            items.addAll(resultList)
        }
        notifyDataSetChanged()
    }

    inner class ListingViewVH(binding: ItemListingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemListingBinding

        init {
            this.binding = binding
        }

        fun bindData(data: Result) {
            binding.root.setOnClickListener {
                itemListener.recyclerViewListClicked(
                    binding.root,
                    layoutPosition
                )
            }
            binding.tvName.text = "Name : ${data.name?.first} ${data.name?.last}"
            binding.tvId.text = "Id : ${data.id?.value}"
            binding.email.text = "Email : ${data.email}"
            binding.tvPhone.text = "Phone : ${data.phone}"
            binding.gender.text = "Gender : ${data.gender}"
            binding.tvDob.text = "DOB : ${data.dob?.date}"
            binding.tvLocation.text = "Location : ${data.location?.city}"
            binding.tvRegistered.text = "Registered : ${data.registered?.date}"
            binding.executePendingBindings()
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemByPosition(position: Int): Result {
        return items[position]
    }

}
