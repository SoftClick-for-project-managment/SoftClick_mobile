package com.job.softclick_mobile.ui.tasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentDetailsTaskBinding;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.utils.PushNotificationHub;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;

public class DetailsTask extends Fragment {
    private FragmentDetailsTaskBinding binding;
    private Task task;
    private TaskViewModel taskViewModel;

    public DetailsTask() {
        // Required empty public constructor
    }

    public static DetailsTask newInstance(String param1, String param2) {
        DetailsTask fragment = new DetailsTask();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable("task");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        binding.detailsBody.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        LiveResponse<Task, Throwable> taskLiveResp = taskViewModel.getSingle(task.getId());

        taskLiveResp.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable th) {
                if (th instanceof HttpException) {
                    binding.backArrow.callOnClick();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (th instanceof IOException) {
                    Toast.makeText(getContext(), "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                Log.d("ERR", th.getMessage());
            }
        });

        taskLiveResp.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task t) {
                task = t;
                refreshUi();
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

                                    Fragment fragment = new TaskForm();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("task", task);
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.flContent, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    Toast.makeText(getActivity(), "ccccccccc" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                                    break;

                                case R.id.delete:
                                    Toast.makeText(getActivity(), "Under Construction ", Toast.LENGTH_LONG).show();
                                    AlertDialog diaBox = AskOption();
                                    diaBox.show();
                                    break;

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

        if (binding.backArrow != null) {
            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, TaskList.newInstance(task.getProjectId())).commit();
                }
            });
        }

        return view;
    }

    private void refreshUi(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.detailsBody.setVisibility(View.VISIBLE);
        binding.TaskNameValue.setText(task.getName());
        binding.statusValue.setText(task.getStatus().getNameStatus());
        binding.Startdatevalue.setText(task.getStartDate());
        binding.EnddateValue.setText(task.getEndDate());
        binding.employeeValue.setText(task.getEmployee().getEmployeeFirstName()+" "+task.getEmployee().getEmployeeLastName());
        if (task.getDescription() != null)
            binding.DescriptionValue.setText(task.getDescription());
        else
            binding.taskDescriptionCard.setVisibility(View.GONE);
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete this Task?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteTask();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
    public void deleteTask(){
        // binding.progressBar.setVisibility(View.VISIBLE);
        //binding.formBody.setVisibility(View.GONE);
        LiveResponse createLiveResponse =  taskViewModel.delete((long) this.task.getId());
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    //binding.progressBar.setVisibility(View.GONE);
                    binding.backArrow.callOnClick();
                }
            }
        });

        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                //binding.formBody.setVisibility(View.VISIBLE);
                //binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }

}
