package com.job.softclick_mobile.ui.projectFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentDetailProjectBinding;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.ui.tasks.TaskList;
import com.job.softclick_mobile.viewmodels.invoices.IInvoiceViewModel;
import com.job.softclick_mobile.viewmodels.invoices.InvoiceViewModel;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;
import com.job.softclick_mobile.viewmodels.teams.ITeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";
    private static final String ARG_PARAM2 = "param2";

    private FragmentDetailProjectBinding binding;
    private IProjectViewModel projectViewModel;
    TextView project_name, domain, date_debut, date_fin, description, name_chef, clients, equips, revenue,priority_name;
    private Button see_tasks_btn;
    LinearProgressIndicator etat_avancement;
    ImageView flesh_back, moreOptions;
    private Long id_project;
    private List<Invoice> invoicesList;
    private IInvoiceViewModel invoiceViewModel;
    private ITeamViewModel teamViewModel;

    // TODO: Rename and change types of parameters
    private Project project;
    private String mParam2;

    public DetailProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProjectFragment newInstance(String param1, String param2) {
        DetailProjectFragment fragment = new DetailProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable(ARG_PARAM1);
            id_project = project.getIdProject();
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProjectBinding.inflate(inflater, container, false);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        project_name = binding.projectName;
        domain = binding.domainProject;
        date_debut = binding.dateDebut;
        date_fin = binding.dateFin;
        description = binding.descriptionProjet;
        name_chef = binding.nameChef;
        clients = binding.clientsText;
        equips = binding.equipesText;
        revenue = binding.revenuTotal;
        etat_avancement = binding.etatAvancement;
        flesh_back = binding.fleshBack;
        moreOptions = binding.moreOptions;
        priority_name = binding.prioritydeprojet;
        see_tasks_btn = binding.seeTasksBtn;

        fetchDate();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();

        flesh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    try {
                        getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, new FooterFragment(ListProjectsFragment.class)).commit();
                        getParentFragmentManager().popBackStackImmediate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        see_tasks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fContentFooter, new FooterFragment(TaskList.class)).commit();
                Log.d("id project : ",id_project.toString());
                fragmentManager.beginTransaction().replace(R.id.flContent, TaskList.newInstance(id_project)).commit();
            }
        });

        if (binding.moreOptions != null) {
            binding.moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), binding.moreOptions);
                    popupMenu.getMenuInflater().inflate(R.menu.details_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            // Toast message on menu item clicked
                            Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            switch (menuItem.getItemId()) {
                                case R.id.edit:

                                    Fragment fragment = new AddProjectFragment();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("project", project);
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.flContent, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    break;

                                case R.id.delete:
                                    showDialogDeleteConfirmation();


                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        return binding.getRoot();

    }


    public void showDialogDeleteConfirmation() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete this project ?");
        builder.setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                projectViewModel.delete(project.getIdProject());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss deletion
            }
        });
        builder.show();
    }

    public void fetchDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


        Invoice invoiceProject = new Invoice("","",null,project);
        invoiceViewModel.search(invoiceProject).gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> invoices) {
                String clients_names = "";
                for (Invoice invoice : invoices
                ) {
                    clients_names += "- " + invoice.getClient().getNom() + " \n";

                }
                clients.setText(clients_names);
            }
        });

        String teams = "";
        if (project.getTeams() != null) {
        for (Team team : project.getTeams()) {
            teams += "- "+team.getTeam_Name() + " \n ";
        }
    }
        equips.setText(teams);

        Double avancement=0d;
        if(project.getTasks() != null && project.getTasks().size()>0) {
            for (Task task : project.getTasks()) {
                if (task.getStatus().getNameStatus().trim().equals("new")) {
                    avancement++;
                }
            }
            avancement = (avancement *100 )/ project.getTasks().size();
        }

        project_name.setText(project.getNameProject());
        domain.setText(project.getDomainProjet().getNameDomain());
        date_debut.setText(formatter.format(project.getDateDebut()));
        date_fin.setText(formatter.format(project.getDateFin()));
        description.setText(project.getDescriptionProject());
        name_chef.setText(project.getChefProject().getEmployeeLastName());
        priority_name.setText(project.getProjectPriority().getNamePriority());
        etat_avancement.setProgress(avancement.intValue());

        revenue.setText(project.getRevenueProject().toString() + " DH ");


    }
}