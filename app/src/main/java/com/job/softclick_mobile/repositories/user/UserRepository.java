package com.job.softclick_mobile.repositories.user;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.services.http.HttpClient;
import com.job.softclick_mobile.services.http.UserApi;
import com.job.softclick_mobile.utils.LiveResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository implements IUserRepository {
    private UserApi apiService;
    LiveResponse<TokenPeer, Throwable> loginLiveResponse = new LiveResponse<>();
    LiveResponse<Boolean, Throwable> signupLiveResponse = new LiveResponse<>();
    LiveResponse<User, Throwable> authenticatedUserLiveResponse = new LiveResponse<>();

    public UserRepository() {
        Retrofit httpClient = HttpClient.getInstance();
        apiService = httpClient.create(UserApi.class);
    }

    public LiveResponse login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        apiService.login(user).enqueue(new Callback<TokenPeer>() {
            @Override
            public void onResponse(Call<TokenPeer> call, Response<TokenPeer> response) {
                if (response.isSuccessful()) {
                    TokenPeer tokens = response.body();
                    loginLiveResponse.gettMutableLiveData().setValue(tokens);
                } else {
                    loginLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<TokenPeer> call, Throwable t) {
                loginLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return loginLiveResponse;
    }

    @Override
    public LiveResponse getAll() {
        return null;
    }

    @Override
    public LiveResponse getSingle(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse create(User user) {
        apiService.create(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    signupLiveResponse.gettMutableLiveData().setValue(true);
                } else {
                    signupLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                signupLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return signupLiveResponse;
    }

    @Override
    public LiveResponse update(Long aLong, User user) {
        return null;
    }

    @Override
    public LiveResponse delete(Long aLong) {
        return null;
    }

    @Override
    public LiveResponse getAuthenticated() {
        apiService.getAuthenticated().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    authenticatedUserLiveResponse.gettMutableLiveData().setValue(response.body());
                } else {
                    authenticatedUserLiveResponse.geteMutableLiveData().setValue(new HttpException(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                authenticatedUserLiveResponse.geteMutableLiveData().setValue(t);
            }
        });

        return authenticatedUserLiveResponse;
    }

    @Override
    public LiveResponse verify(String code) {
        return null;
    }
}
