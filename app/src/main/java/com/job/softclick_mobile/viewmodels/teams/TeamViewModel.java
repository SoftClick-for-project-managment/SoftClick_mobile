package com.job.softclick_mobile.viewmodels.teams;

import android.app.Application;

import androidx.annotation.NonNull;


import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.repositories.teams.TeamsRepository;
import com.job.softclick_mobile.viewmodels.BaseViewModel;


public class TeamViewModel extends BaseViewModel<Team, Long> implements ITeamViewModel {
    public TeamViewModel(@NonNull Application application) {
        super(application, new TeamsRepository());
    }


}
