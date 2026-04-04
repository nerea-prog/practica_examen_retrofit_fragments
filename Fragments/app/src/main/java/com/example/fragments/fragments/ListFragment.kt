package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragments.databinding.FragmentListBinding
import com.example.fragments.R
import com.example.fragments.adapters.ProductAdapter
import com.example.fragments.viewmodel.ProductViewModel


class ListFragment : Fragment() {


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = ProductAdapter(
            onEdit = { product ->
                viewModel.selectProduct(product)
                // Navegar al UpdateFragment
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, UpdateFragment())
                    .addToBackStack(null)
                    .commit()
            },
            onDelete = { product ->
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar producto")
                    .setMessage("¿Seguro que quieres eliminar '${product.name}'?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        viewModel.deleteProduct(product.id)
                    }
                    .setNegativeButton("Cancelar", null)  // null = solo cierra el dialogo
                    .show()
            }

        )


        // 2. Configurar RecyclerView
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProducts.adapter = adapter


        // 3. Observar LiveData
        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.message.observe(viewLifecycleOwner) { msg ->
            msg?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }


        // 4. FAB -> navegar a AddFragment
        binding.fabAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddFragment())
                .addToBackStack(null)
                .commit()
        }


        // 5. Cargar datos al entrar
        viewModel.loadProducts()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Evitar memory leak
    }
}
