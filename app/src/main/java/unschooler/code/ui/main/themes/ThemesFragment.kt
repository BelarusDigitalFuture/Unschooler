package unschooler.code.ui.main.themes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_schedule.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import unschooler.code.NavMainGraphDirections

import unschooler.code.R
import unschooler.code.ui.main.themes.common.SubjectsAdapter
import unschooler.code.utils.alertOk
import unschooler.code.utils.navigateSafe
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ThemesFragment : DaggerFragment(R.layout.f_schedule) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ThemesViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(requireContext())

        filter.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            list.adapter = SubjectsAdapter(it,
                subjectListener = {
                    requireActivity().alertOk(R.string.ui_in_develop)
                },
                themeListener = {
                    findNavController().navigateSafe(NavMainGraphDirections.openTheme(it.id, null))
                },
                themeButtonListener = {
//                    requireActivity().alertOk(R.string.ui_in_develop)
                })
        })
    }

}
