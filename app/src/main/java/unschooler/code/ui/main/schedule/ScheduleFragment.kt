package unschooler.code.ui.main.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.f_schedule.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

import unschooler.code.R
import unschooler.code.ui.main.schedule.common.SubjectsAdapter
import unschooler.code.utils.alertOk
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ScheduleFragment : DaggerFragment(R.layout.f_schedule) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ScheduleViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(requireContext())

        filter.setOnClickListener {
            requireActivity().alertOk(R.string.ui_in_develop)
        }

        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            list.adapter = SubjectsAdapter(it){
                requireActivity().alertOk(R.string.ui_in_develop)
            }
        })
    }

}
