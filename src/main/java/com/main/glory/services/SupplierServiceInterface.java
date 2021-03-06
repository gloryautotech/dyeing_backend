package com.main.glory.services;

import com.main.glory.model.supplier.SupplierRate;
import com.main.glory.model.supplier.requestmodals.AddSupplierRateRequest;
import com.main.glory.model.supplier.Supplier;
import com.main.glory.model.supplier.requestmodals.UpdateSupplierRatesRequest;
import com.main.glory.model.supplier.requestmodals.UpdateSupplierRequest;

import java.util.List;
import java.util.Optional;

public interface SupplierServiceInterface {
    public Boolean addSupplier(Supplier supplier);
    public Boolean isUniqueName(Long id, String name);
    public Boolean addSupplierRates(AddSupplierRateRequest addSupplierRateRequest);
    public Object getSupplier(Long id);
    public Boolean updateSupplier(UpdateSupplierRequest updateSupplierRequest);
    public void updateSupplierRates(UpdateSupplierRatesRequest updateSupplierRatesRequest) throws Exception;
    public List getAllSupplier(String getBy, Long id) throws Exception;


}
