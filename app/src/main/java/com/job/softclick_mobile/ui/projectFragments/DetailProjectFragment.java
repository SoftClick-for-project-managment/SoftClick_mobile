package com.job.softclick_mobile.ui.projectFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentDetailProjectBinding;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.ui.FooterFragment;

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
    TextView project_name, domain, date_debut, date_fin, description, name_chef, clients, equips, revenue, depense;
    LinearProgressIndicator etat_avancement;
    ImageView flesh_back, moreOptions;

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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProjectBinding.inflate(inflater, container, false);

        project_name = binding.projectName;
        domain = binding.domainProject;
        date_debut = binding.dateDebut;
        date_fin = binding.dateFin;
        description = binding.descriptionProjet;
        name_chef = binding.nameChef;
        clients = binding.clientsText;
        equips = binding.equipesText;
        revenue = binding.revenuTotal;
        depense = binding.depenseTotal;
        etat_avancement = binding.etatAvancement;
        flesh_back = binding.fleshBack;
        moreOptions = binding.moreOptions;

        fetchDate();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();

        flesh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    try {
                        getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                        getParentFragmentManager().popBackStackImmediate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
                //to do delete project
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
        project_name.setText(project.getNameProject());
        domain.setText("Intelligance Artificial & Big Data");
        date_debut.setText("18/12/2021");
        date_fin.setText("18/12/2022");
        description.setText(project.getDescriptionProject());
        name_chef.setText("Mr Youssef Gahi");
        clients.setText("- oukacha prison \n - école sanabil \n - Army American");
        equips.setText("- équipe frontend N° 1 \n équipe fullstack N° 5");
        revenue.setText(project.getRevenue().toString() + " DH ");
        depense.setText("200000 DH");
        etat_avancement.setProgress(25);
    }
}