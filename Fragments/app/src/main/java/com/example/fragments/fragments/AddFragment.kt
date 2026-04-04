package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fragments.databinding.FragmentAddBinding
import com.example.fragments.model.Product
import com.example.fragments.viewmodel.ProductViewModel


class AddFragment : Fragment() {


    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Boton Guardar
        binding.btnSave.setOnClickListener {
            val name  = binding.etName.text.toString().trim()
            val price = binding.etPrice.text.toString().trim()
            val desc  = binding.etDescription.text.toString().trim()


            if (name.isEmpty() || price.isEmpty() || desc.isEmpty()) {
                Toast.makeText(context, "Rellena todos los campos",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val product = Product(
                id = "",
                name = name,
                price = price.toDoubleOrNull() ?: 0.0,
                description = desc)


            viewModel.createProduct(product)


            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}