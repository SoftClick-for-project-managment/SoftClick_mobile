package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentDetailsTaskBinding;
import com.job.softclick_mobile.databinding.FragmentPrincipalBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.repositories.teams.ITeamsRepository;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.expense.ExpenseViewModel;
import com.job.softclick_mobile.viewmodels.expense.IExpenseViewModel;
import com.job.softclick_mobile.viewmodels.invoices.IInvoiceViewModel;
import com.job.softclick_mobile.viewmodels.invoices.InvoiceViewModel;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;
import com.job.softclick_mobile.viewmodels.teams.ITeamViewModel;
import com.job.softclick_mobile.viewmodels.teams.TeamViewModel;

import java.util.List;

public class PrincipalFragment extends Fragment {
    private FragmentPrincipalBinding binding;
    private ITaskViewModel taskViewModel;
    private IClientViewModel clientViewModel;
    private IEmployeeViewModel employeeViewModel;
    private ITeamViewModel teamViewModel;
    private IInvoiceViewModel invoiceViewModel;
    private IExpenseViewModel expenseViewModel;
    private IProjectViewModel projectViewModel;
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public PrincipalFragment() {
        // Required empty public constructor
    }

    public static PrincipalFragment newInstance(String param1, String param2) {
        PrincipalFragment fragment = new PrincipalFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        loadingLiveData.setValue(true);
        loadingLiveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {

                } else {

                }
            }
        });

        taskViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Task> taskList = (List<Task>) o;
                binding.taskCard.taskssnumber.setText(""+taskList.size());
            }
        });
        employeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Employee> employeeList = (List<Employee>) o;
                binding.Employeesnumber.setText(""+""+employeeList.size());
            }
        });
        clientViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Client> clientList = (List<Client>) o;
                binding.clientsCount.setText(""+clientList.size());
            }
        });
        invoiceViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Invoice> invoiceList = (List<Invoice>) o;
                binding.invoiceCard.invoicesnumber.setText(""+invoiceList.size());
            }
        });
        teamViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Team> teamList = (List<Team>) o;
                binding.Teamsnumber.setText(""+teamList.size());
            }
        });
        expenseViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Expense> expenseList = (List<Expense>) o;
                binding.expenseCard.expensesnumber.setText(""+expenseList.size());
                loadingLiveData.setValue(false);
            }
        });
        projectViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                List<Project> projectList = (List<Project>) o;
                binding.projectCard.projectsnumber.setText(""+projectList.size());
                loadingLiveData.setValue(false);
            }
        });

        return view;
    }
}