package assignment.hyparz.activities.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import assignment.hyparz.R
import assignment.hyparz.dataRepository.backend.model.Result
import assignment.hyparz.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        val details = extras?.getSerializable("details") as Result
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
//        binding.details = details
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        binding.tvName.text = "Name : ${details.name?.first} ${details.name?.last}"
        binding.tvId.text = "Id : ${details.id?.value}"
        binding.email.text = "Email : ${details.email}"
        binding.tvPhone.text = "Phone : ${details.phone}"
        binding.gender.text = "Gender : ${details.gender}"
        binding.tvDob.text = "DOB : ${details.dob?.date}"
        binding.tvLocation.text = "Location : ${details.location?.city}"
        binding.tvRegistered.text = "Registered : ${details.registered?.date}"
    }
}