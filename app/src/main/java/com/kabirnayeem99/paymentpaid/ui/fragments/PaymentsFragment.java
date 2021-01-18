package com.kabirnayeem99.paymentpaid.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter;
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;


public class PaymentsFragment extends Fragment {
    RecyclerView rvPaymentListByMonth;
    TextView tvPaymentTotal;
    PaymentAdapter paymentAdapter;

    public static PaymentsFragment getInstance() {
        return new PaymentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);
        WorkViewModel workViewModel = ViewModelProviders.of(requireActivity()).get(WorkViewModel.class);
        paymentAdapter = new PaymentAdapter();

        workViewModel.getTotalPaymentByMonth().observe(requireActivity(),
                integers -> paymentAdapter.setMonthlyPaymentList(integers));

        workViewModel.getTotalPaymentByYear().observe(requireActivity(),
                integer -> tvPaymentTotal.setText(String.valueOf(integer == null ? 0 :
                        CustomUtils.formatMoney(integer.toString()))));
        initRecyclerView();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(@NonNull View view) {
        rvPaymentListByMonth = view.findViewById(R.id.rv_payment_list_by_month_payments);
        tvPaymentTotal = view.findViewById(R.id.tv_payment_list_total_payments);
    }

    private void initRecyclerView() {

        rvPaymentListByMonth.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvPaymentListByMonth.setHasFixedSize(true);
        rvPaymentListByMonth.setAdapter(paymentAdapter);
    }

}