package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments.R
import com.example.fragments.adapters.ProductAdapter
import com.example.fragments.viewmodel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout normal
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buscamos las vistas con findViewById
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerProducts)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        adapter = ProductAdapter(
            onEdit = { product ->
                viewModel.selectProduct(product)
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
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            msg?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }

        fabAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadProducts()
    }
}
