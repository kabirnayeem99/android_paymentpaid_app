package com.kabirnayeem99.paymentpaid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;
import com.kabirnayeem99.paymentpaid.utils.DatabaseUtils;

import java.util.Map;


public class PaymentsFragment extends Fragment {
    private static final String TAG = "PaymentsFragment";
    RecyclerView rvPaymentListByMonth;
    DatabaseUtils databaseUtils;
    TextView tvPaymentTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);

        databaseUtils = new DatabaseUtils(getActivity());
        tvPaymentTotal.setText(CustomUtils.formatNumber(databaseUtils.getTotalPaymentByYear()));

        initRecyclerView(databaseUtils);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(@NonNull View view) {
        rvPaymentListByMonth = view.findViewById(R.id.rv_payment_list_by_month_payments);
        tvPaymentTotal = view.findViewById(R.id.tv_payment_list_total_payments);
    }

    private void initRecyclerView(DatabaseUtils databaseUtils) {
        Map<Integer, Integer> totalPaymentListByMont = databaseUtils.getTotalPaymentByMonth();

        Log.d(TAG, "initRecyclerView: " + totalPaymentListByMont);

        PaymentAdapter paymentAdapter = new PaymentAdapter(totalPaymentListByMont);

        rvPaymentListByMonth.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPaymentListByMonth.setAdapter(paymentAdapter);
    }

}