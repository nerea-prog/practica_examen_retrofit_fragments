package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fragments.R
import com.example.fragments.model.Product
import com.example.fragments.viewmodel.ProductViewModel


class UpdateFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etUpdateName)
        val etPrice = view.findViewById<EditText>(R.id.etUpdatePrice)
        val etDescription = view.findViewById<EditText>(R.id.etUpdateDescription)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        val product = viewModel.selectedProduct.value
        product?.let {
            etName.setText(it.name)
            etPrice.setText(it.price.toString())
            etDescription.setText(it.description)
        }

        btnUpdate.setOnClickListener {
            val name  = etName.text.toString().trim()
            val price = etPrice.text.toString().trim()
            val desc  = etDescription.text.toString().trim()

            if (name.isEmpty() || price.isEmpty() || desc.isEmpty()) {
                Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updated = Product(
                id = product?.id ?: "",
                name = name,
                price = price.toDoubleOrNull() ?: 0.0,
                description = desc
            )

            viewModel.updateProduct(updated.id, updated)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
