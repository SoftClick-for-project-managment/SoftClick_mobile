package com.job.softclick_mobile;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.repositories.clients.ClientRepository;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Mock
    Context mockContext;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private ClientRepository repository;
    private ClientViewModel viewModel;




}