package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fragments.databinding.FragmentUpdateBinding
import com.example.fragments.model.Product
import com.example.fragments.viewmodel.ProductViewModel


class UpdateFragment : Fragment() {


    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 1. Obtener el producto seleccionado del ViewModel y pre-rellenar campos
        val product = viewModel.selectedProduct.value
        product?.let {
            binding.etUpdateName.setText(it.name)
            binding.etUpdatePrice.setText(it.price.toString())
            binding.etUpdateDescription.setText(it.description)
        }


        // 2. Boton Actualizar
        binding.btnUpdate.setOnClickListener {
            val name  = binding.etUpdateName.text.toString().trim()
            val price = binding.etUpdatePrice.text.toString().trim()
            val desc  = binding.etUpdateDescription.text.toString().trim()


            if (name.isEmpty() || price.isEmpty() || desc.isEmpty()) {
                Toast.makeText(context, "Rellena todos los campos",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Modificado para usar id como String
            val updated = Product(
                id = product?.id ?: "",
                name = name,
                price = price.toDoubleOrNull() ?: 0.0,
                description = desc
            )


            // 3. Enviar actualizacion
            viewModel.updateProduct(updated.id, updated)


            // 4. Volver a la lista
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
