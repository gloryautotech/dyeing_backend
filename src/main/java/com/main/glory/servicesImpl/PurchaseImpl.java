package com.main.glory.servicesImpl;

import com.main.glory.Dao.SupplierRateDao;
import com.main.glory.Dao.admin.DepartmentDao;
import com.main.glory.Dao.admin.ReceiverByDao;
import com.main.glory.Dao.purchase.PurchaseDao;
import com.main.glory.model.admin.ApprovedBy;
import com.main.glory.model.admin.Department;
import com.main.glory.model.admin.ReceiverBy;
import com.main.glory.model.purchase.Purchase;

import com.main.glory.model.purchase.response.PurchaseResponse;

import com.main.glory.model.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("purchaseOrderImpl")
public class PurchaseImpl {
    @Autowired
    AdminServciceImpl adminServcice;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    SupplierServiceImpl supplierService;

    @Autowired
    SupplierRateDao supplierRateDao;


    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    ReceiverByDao receiverByDao;

    public void addPurchase(Purchase record) throws Exception {
        //check the department and receiver exit

        Department departmentExist = departmentDao.getDepartmentById(record.getDepartmentId());
        ReceiverBy receiverByExist = receiverByDao.getReceiverById(record.getReceiverById());
        ApprovedBy approvedByExist = adminServcice.getApprovedById(record.getApprovedById());
        if (receiverByExist==null || departmentExist==null )
            throw new Exception("no receiver or department found");

        if (approvedByExist==null)
            throw new Exception("no approved by found");

        purchaseDao.save(record);



    }

    public void updatePurchase(Purchase record, String id) throws Exception {

        Department departmentExist = departmentDao.getDepartmentById(record.getDepartmentId());
        ReceiverBy receiverByExist = receiverByDao.getReceiverById(record.getReceiverById());
        ApprovedBy approvedByExist = adminServcice.getApprovedById(record.getApprovedById());
        if (receiverByExist==null || departmentExist==null )
            throw new Exception("no receiver or department found");

        if (approvedByExist==null)
            throw new Exception("no approved by found");

        //check the record updated by admin or user
        UserData userDataExist = userService.getUserById(Long.parseLong("id"));
        if(userDataExist==null)
            throw new Exception("no user record found");

        Purchase purchaseRecordExist = purchaseDao.getPurchaseById(record.getId());
        if(record.getChecked()!=purchaseRecordExist.getChecked())
        {
            if(userDataExist.getUserHeadId()!=0)
            throw new Exception("Only admin can update the status of purchase record");
            else
            {
                purchaseDao.saveAndFlush(record);
                return;
            }
        }
        else {

            purchaseDao.saveAndFlush(record);
            return;
        }


    }

    public List<PurchaseResponse> getAllPurchaseRecord() {
        List<PurchaseResponse> responses=purchaseDao.getAllPurchaseRecord();

        return responses;
    }

    public PurchaseResponse getPurchaseRecordById(Long id) {
        return purchaseDao.getPurchaseResponseById(id);
    }

    public List<Purchase> getPurchaseByReceiverId(Long id) {
        return purchaseDao.getAllPurchaseByReceiverById(id);
    }

    public void updatePurchaseRecordWithAdmin(Long id, Boolean flag, String id1) throws Exception {

        Purchase purchaseExist = purchaseDao.getPurchaseById(id);
        if(purchaseExist==null)
            throw new Exception("no record foun");


        UserData userData = userService.getUserById(Long.parseLong(id1));
        if(userData==null)
            throw new Exception("no user found");

        if(userData.getUserHeadId()!=0)
            throw new Exception("record can vbe updated by only admin");


        purchaseDao.updateStatus(id,flag);



    }



    /*public Boolean addPurchaseOrder(PurchaseOrder purchaseOrder) throws Exception{

        if(purchaseOrder.getStatus()==1 || purchaseOrder.getStatus()==2 || purchaseOrder.getStatus()==0)
        {
            Optional<Supplier> supplierExist=supplierService.getSupplier(purchaseOrder.getSupplierId());
            Optional<SupplierRate> supplierItemExist=supplierRateDao.findByIdAndSupplierId(purchaseOrder.getItemId(),purchaseOrder.getSupplierId());

            if(!supplierExist.isPresent())
                throw new Exception("Supplier is not available with name:"+purchaseOrder.getSupplierName());

            if(supplierItemExist.isEmpty())
                throw new Exception("Item is not availble for supplier:"+ purchaseOrder.getSupplierName());

            purchaseOrderDao.save(purchaseOrder);
            return true;
        }
        return false;
    }


    public List<ResponsePurchase> getAllPurchaseOrder() throws Exception {
        List<ResponsePurchase> responsePurchasesList=new ArrayList<>();

        List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getAllPurchaseOrder();
        for(PurchaseOrder purchaseOrder:purchaseOrderList)
        {
            if(purchaseOrder.getItemId()!=null && purchaseOrder.getSupplierId()!=null)
            {
                Optional<Supplier> supplierExist=supplierService.getSupplier(purchaseOrder.getSupplierId());
                Optional<SupplierRate> supplierItemExist = supplierService.getItemById(purchaseOrder.getItemId());
                UserData user = userService.getUserById(purchaseOrder.getUserHeadId());

                if(!supplierExist.isPresent() && !supplierItemExist.isPresent() && user==null )
                    continue;

                ResponsePurchase responsePurchase = new ResponsePurchase(purchaseOrder,user.getUserName());
                responsePurchasesList.add(responsePurchase);
            }
        }
        if(responsePurchasesList.isEmpty())
            throw new Exception("no data found");
        return responsePurchasesList;
    }

    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
        Optional<PurchaseOrder> purchaseExist = purchaseOrderDao.findById(purchaseOrder.getId());

        if(!purchaseExist.isPresent())
            throw new Exception("Data not found for id:"+purchaseOrder.getId());

        Optional<Supplier> supplierExist=supplierService.getSupplier(purchaseOrder.getSupplierId());
        Optional<SupplierRate> supplierItemExist=supplierService.getItemById(purchaseOrder.getItemId());

        if(!supplierExist.isPresent())
            throw new Exception("Supllier is not available with name:"+purchaseOrder.getSupplierName());

        if(!supplierItemExist.isPresent())
            throw new Exception("Item is not availble for supplier:"+ purchaseOrder.getSupplierName());


        purchaseOrderDao.saveAndFlush(purchaseOrder);

    }*/
}
