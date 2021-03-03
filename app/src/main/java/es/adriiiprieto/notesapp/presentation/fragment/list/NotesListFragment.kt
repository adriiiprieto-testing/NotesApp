package es.adriiiprieto.notesapp.presentation.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.adriiiprieto.notesapp.R
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseFragment
import es.adriiiprieto.notesapp.databinding.FragmentNotesListBinding
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

@AndroidEntryPoint
class NotesListFragment : BaseFragment<NotesListState, NotesListViewModel, FragmentNotesListBinding>() {

    override val viewModelClass: Class<NotesListViewModel> = NotesListViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesListBinding = FragmentNotesListBinding::inflate

    lateinit var mAdapter: NotesListAdapter

    lateinit var vm: NotesListViewModel

    override fun setupView(viewModel: NotesListViewModel) {
        vm = viewModel

        setupRecyclerView()
        setupButton()
    }

    override fun onNormal(data: NotesListState) {
        binding.fragmentNotesListImageViewNoContent.visibility = if (data.notesList.isNotEmpty()) View.GONE else View.VISIBLE
        binding.fragmentNotesListRecyclerView.visibility = if (data.notesList.isEmpty()) View.GONE else View.VISIBLE

        if (data.notesList.isNotEmpty()) {
            mAdapter.updateList(data.notesList)
        }
    }

    override fun onLoading(dataLoading: BaseExtraData?) {

    }

    override fun onError(dataError: Throwable) {

    }

    /**
     * Setup Views
     */
    private fun setupButton() {
        binding.fragmentNotesListFab.setOnClickListener {
            findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment())
        }
    }

    private fun setupRecyclerView() {
        mAdapter = NotesListAdapter(listOf(), object : NotesListAdapter.MyClicksListener {
            override fun onEditButtonClicked(item: NoteDomainModel) {
                findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment(item))
            }

            override fun onDeleteButtonClicked(item: NoteDomainModel) {
                vm.onActionDeleteNote(item)
            }
        })
        binding.fragmentNotesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

}