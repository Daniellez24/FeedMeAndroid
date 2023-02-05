package com.example.feedme.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.feedme.models.Tip;
import com.example.feedme.models.TipModel;

import java.util.List;

public class TipsFragmentViewModel extends ViewModel {

    private LiveData<List<Tip>> data = TipModel.instance.getTipById(3550);

    public LiveData<List<Tip>> getData(){
        return data;
    }

}
