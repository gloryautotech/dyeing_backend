package com.main.glory.servicesImpl;

import com.main.glory.Dao.SupplierRateDao;
import com.main.glory.Dao.color.ColorBoxDao;
import com.main.glory.Dao.color.ColorDataDao;
import com.main.glory.Dao.color.ColorMastDao;
import com.main.glory.Dao.SupplierDao;
import com.main.glory.Dao.user.UserDao;
import com.main.glory.model.ConstantFile;
import com.main.glory.model.color.ColorAcknowledgement;
import com.main.glory.model.color.ColorBox;
import com.main.glory.model.color.ColorData;
import com.main.glory.model.color.ColorMast;
import com.main.glory.model.color.request.GetAllBox;
import com.main.glory.model.color.request.IssueBoxRequest;
import com.main.glory.model.color.responsemodals.ColorMastDetails;
import com.main.glory.model.color.responsemodals.SupplierItemWithLeftColorQty;
import com.main.glory.model.supplier.Supplier;
import com.main.glory.model.supplier.SupplierRate;
import com.main.glory.model.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl {

    @Autowired
    SupplierRateDao supplierRateDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ColorMastDao colorMastDao;

    @Autowired
    ColorDataDao colorDataDao;

    @Autowired
    ColorBoxDao colorBoxDao;

    @Autowired
    SupplierDao supplierDao;

    ConstantFile constantFile;

    @Transactional
    public void addColor(ColorMast colorMast,String id) throws Exception {

        //identify the record is addedby the data entry user
        //for data entry user
        UserData user = userDao.getUserById(Long.parseLong(id));
        if(user.getIsMaster()==false)
        {
            Optional<Supplier> supplier = supplierDao.getSupplierById(colorMast.getSupplierId());
            if(supplier.isEmpty())
                throw new Exception(constantFile.Supplier_Found);
            colorMast.setUserHeadId(supplier.get().getUserHeadId());
        }



        ColorMast colorMast1 = colorMastDao.save(colorMast);
        colorMast1.getColorDataList().forEach(e -> {
            e.setControlId(colorMast1.getId());
        });
        colorDataDao.saveAll(colorMast1.getColorDataList());

        List<ColorBox> colorBoxes = new ArrayList<>();
        List<ColorData> cd = colorDataDao.findAllByControlId(colorMast1.getId());
        if (!cd.isEmpty()) {
            cd.forEach(e -> {
                for (int i = 0; i < e.getNoOfBox(); i++) {
                    ColorBox temp = new ColorBox();
                    temp.setControlId(e.getId());
                    temp.setIssued(false);
                    temp.setFinished(false);
                    temp.setQuantity(e.getQuantityPerBox());
                    temp.setQuantityLeft((double) e.getQuantityPerBox());
                    colorBoxes.add(temp);
                }
            });
            colorBoxDao.saveAll(colorBoxes);
        }
    }

    public List<ColorMastDetails> getAll(String getBy, Long id) throws Exception {
        List<ColorMastDetails> colorMastDetails = new ArrayList<>();

        if (id == null) {
            List<ColorMast> data = colorMastDao.getAllColorList();
            data.forEach(e -> {
                try {
                    ColorMastDetails x = new ColorMastDetails(e);
                    String name = supplierDao.findById(e.getSupplierId()).get().getSupplierName();
                    if (!name.isEmpty()) {
                        x.setSupplierName(name);
                        colorMastDetails.add(x);
                    }

					/*e.getColorDataList().forEach(e1->{
						e1.setColorBoxes(colorBoxDao.findAllByControlId(e1.getId()));
					});*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } else if (getBy.equals("own")) {
            List<ColorMast> data = colorMastDao.getAllByCreatedBy(id);
            data.forEach(e -> {
                try {
                    ColorMastDetails x = new ColorMastDetails(e);
                    String name = supplierDao.findById(e.getSupplierId()).get().getSupplierName();
                    if (!name.isEmpty()) {
                        x.setSupplierName(name);
                        colorMastDetails.add(x);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } else if (getBy.equals("group")) {
            UserData userData = userDao.findUserById(id);
            if(userData.getUserHeadId().equals(userData.getId()))
            {
                //master user
                List<ColorMast> data = colorMastDao.getAllByCreatedByAndHeadId(id,id);
                data.forEach(e -> {
                    try {
                        ColorMastDetails x = new ColorMastDetails(e);
                        String name = supplierDao.findById(e.getSupplierId()).get().getSupplierName();
                        if (!name.isEmpty()) {
                            x.setSupplierName(name);
                            colorMastDetails.add(x);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });



            }
            else
            {
                UserData userOperator=userDao.getUserById(id);
                // operator
                List<ColorMast> data = colorMastDao.getAllByCreatedByAndHeadId(userOperator.getUserHeadId(),userOperator.getUserHeadId());
                data.forEach(e -> {
                    try {
                        ColorMastDetails x = new ColorMastDetails(e);
                        String name = supplierDao.findById(e.getSupplierId()).get().getSupplierName();
                        if (!name.isEmpty()) {
                            x.setSupplierName(name);
                            colorMastDetails.add(x);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

            }





    }
		/*if(colorMastDetails.isEmpty())
                throw new Exception(commonMessage.Color_Not_Found);
*/
		return colorMastDetails;
}

    public List<ColorMastDetails> getOne(Long id) {
        List<ColorMast> data = colorMastDao.getAllColorList();
        List<ColorMastDetails> colorMastDetails = new ArrayList<>();
        data.forEach(e -> {
            try {
                ColorMastDetails x = new ColorMastDetails(e);
                x.setSupplierName(supplierDao.findById(e.getSupplierId()).get().getSupplierName());
                colorMastDetails.add(x);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return colorMastDetails;
    }

    @Transactional
    public boolean updateColor(ColorMast colorMast) throws Exception {
        Optional<ColorMast> original = colorMastDao.findById(colorMast.getId());

        if (original.isEmpty()) {
            throw new Exception(constantFile.Color_Not_Found + colorMast.getId());
        }
        colorMastDao.save(colorMast);
        colorDataDao.deleteColorWhichIsNull();
        colorBoxDao.deleteColorBoxWhichIsNUll();
        return true;
    }

    @Transactional
    public boolean deleteColorById(Long id) throws Exception {
        Optional<ColorMast> colorMast = colorMastDao.findById(id);

        // check if this is present in the database
        if (colorMast.isEmpty()) {
            throw new Exception(constantFile.Color_Not_Found + id);
        }

        colorMastDao.deleteById(id);

        return true;
    }

    public Optional<ColorMast> getColorById(Long id) {
        var getData = colorMastDao.findById(id);
        if (getData.isPresent())
            return getData;
        return null;
    }

    public List<ColorBox> getAllBox(Boolean issued) throws Exception {
        List<ColorBox> colorBoxes = colorBoxDao.findByIssued(issued);
        if (colorBoxes.isEmpty())
            throw new Exception(constantFile.Color_Not_Found);
        return colorBoxes;
    }

    public void issueBox(IssueBoxRequest issueBoxRequest) throws Exception {
        Optional<ColorBox> colorBox = colorBoxDao.findById(issueBoxRequest.getBoxId());
        if (colorBox.isEmpty()) {
            throw new Exception(constantFile.Color_Not_Found);
        }


        if (colorBox.get().getIssued() == true)
            throw new Exception(constantFile.Color_Already_Issue + colorBox.get().getIssuedDate());

        ColorBox colorBox1 = colorBox.get();
        colorBox1.setIssued(true);
        colorBox1.setIssuedDate(new Date(System.currentTimeMillis()));
        colorBoxDao.save(colorBox1);
    }

    public List<ColorData> getColorByItemId(Long itemId) throws Exception {
        List<ColorData> list = colorDataDao.findByItemId(itemId);
        if (list.isEmpty())
            throw new Exception("no color data found for item id:" + itemId);

        return list;

    }

    public List<ColorBox> getIssuedColorBoxByColorId(Long id) throws Exception {
        //id is control id

        Optional<ColorData> colorDataExist = colorDataDao.findByColorDataId(id);
        if (colorDataExist.isEmpty())
            throw new Exception("no color data found");

        List<ColorBox> listOfColorBoxIssued = colorBoxDao.findAllByControlIdAndIssused(colorDataExist.get().getId());

        if (listOfColorBoxIssued.isEmpty())
            throw new Exception("may the color box is not available or issued");
        return listOfColorBoxIssued;
    }

    public List<ColorBox> getColorBoxListByItemId(Long itemId) {
        List<ColorBox> list = colorDataDao.findAllIssuedBoxByItemId(itemId);
        return list;
    }

    public List<GetAllBox> getAllBoxNotIssuedBoxByItemId(Long itemId, Boolean issued) throws Exception {
        List<GetAllBox> list = new ArrayList<>();

        List<ColorData> colorData = colorDataDao.findByItemId(itemId);
        for (ColorData c : colorData)
        {
            SupplierRate supplierRate = supplierRateDao.getSupplierRateByItemId(c.getItemId());
            Supplier supplier=supplierDao.findBySupplierId(supplierRate.getSupplierId());
            List<ColorBox> colorBoxes = colorBoxDao.getAllBoxByControlIdWithFlag(c.getId(),issued);
            for(ColorBox colorBox:colorBoxes)
            {
                list.add(new GetAllBox(colorBox,supplierRate,supplier));
            }
        }

        /*if(list.isEmpty())
            throw new Exception(constantFile.Color_Not_Found);*/
        return list;

    }

    public List<SupplierItemWithLeftColorQty> getSupplierItemWithAvailableStock() {

        List<SupplierItemWithLeftColorQty> list=new ArrayList<>();

        Double leftQty=0.0;
        Double packedQty=0.0;
        /*List<ItemWithLeftQty> leftQtyList = colorBoxDao.getAllLeftQtyItemList();

        for(ItemWithLeftQty itemWithLeftQty:leftQtyList)
        {
            //System.out.println(itemWithLeftQty.getItemId()+":"+ itemWithLeftQty.getAvailableQty());
            if(itemWithLeftQty==null){
                Supplier supplier = supplierRateDao.getSupplierByItemId(itemWithLeftQty.getItemId());
                SupplierRate supplierRate = supplierRateDao.getSupplierRateByItemId(itemWithLeftQty.getItemId());
                list.add(new SupplierItemWithLeftColorQty(supplier,supplierRate));
            }
            else
            {
                Supplier supplier = supplierRateDao.getSupplierByItemId(itemWithLeftQty.getItemId());
                SupplierRate supplierRate = supplierRateDao.getSupplierRateByItemId(itemWithLeftQty.getItemId());
                list.add(new SupplierItemWithLeftColorQty(itemWithLeftQty,supplier,supplierRate));
            }

        }*/

        List<Supplier> supplierList =supplierDao.getAllSupplierList();
        for(Supplier supplier:supplierList)
        {
            List<SupplierRate> supplierRatesList= supplierRateDao.getItemBySupplier(supplier.getId());
            for(SupplierRate supplierRate:supplierRatesList)
            {
                List<ColorData> colorDataList  = colorDataDao.getAllColorDataByItemId(supplierRate.getId());
                if(colorDataList!=null) {
                    for (ColorData colorData : colorDataList) {

                        List<ColorBox> colorBoxes = colorBoxDao.getAllBoxesByControlId(colorData.getId());
                        for(ColorBox colorBox:colorBoxes)
                        {
                            if(colorBox.getIssued()==true)
                            {
                                leftQty+=colorBox.getQuantityLeft();
                            }
                            else
                                packedQty+=colorBox.getQuantityLeft();
                        }

                    }
                    list.add(new SupplierItemWithLeftColorQty(supplier, supplierRate, leftQty,packedQty));
                }
                else
                    list.add(new SupplierItemWithLeftColorQty(supplier,supplierRate,0.0,0.0));

                leftQty=0.0;
                packedQty=0.0;
            }
        }
        return list;

    }

    public ColorBox getColorBoxById(Long boxId) {
        ColorBox colorBox=colorBoxDao.getColorBoxById(boxId);
        return colorBox;
    }

    public List<GetAllBox> getAllColorBoxes() throws Exception {
        List<GetAllBox> list =new ArrayList<>();
        List<Supplier> supplierList =supplierDao.getAllSupplierList();
        for(Supplier supplier:supplierList)
        {
            List<SupplierRate> supplierRateList =supplierRateDao.findBySupplierId(supplier.getId());
            for(SupplierRate supplierRate:supplierRateList)
            {
                List<ColorData> colorDataList=colorDataDao.findByItemId(supplierRate.getId());
                for(ColorData colorData:colorDataList)
                {
                    List<ColorBox> colorBoxList = colorBoxDao.findAllByControlId(colorData.getId());
                    for(ColorBox colorBox:colorBoxList)
                    {
                        list.add(new GetAllBox(colorBox,supplierRate,supplier));
                    }
                }
            }
        }
	   /* if(list.isEmpty())
            throw new Exception(commonMessage.Color_Not_Found);
*/
        return list;
    }

    public List<ColorMast> getColorByCreatedAndUserHeadId(Long id, Long id1) {
        return colorMastDao.getAllByCreatedByAndHeadId(id,id1);
    }

    public void issueBoxList(List<IssueBoxRequest> issueBoxRequest) {
        //issue all the box
        List<ColorBox> list = new ArrayList<>();
        issueBoxRequest.forEach(e->{
            ColorBox colorBox = colorBoxDao.getColorBoxById(e.getBoxId());
            if(colorBox!=null)
            {
                colorBox.setIssued(true);
                colorBox.setIssuedDate(new Date(System.currentTimeMillis()));

                list.add(colorBox);
            }
        });

        colorBoxDao.saveAll(list);
    }


    //change the existing color stock qty and visible color qty based on issue box
    public void changeExistingColorStock(ColorAcknowledgement record) throws Exception {

       /* //check that the item is exist or not
        SupplierRate supplierRate =supplierRateDao.getSupplierRateByItemId(record.getItemId());

        if(supplierRate==null)
            throw new Exception(ConstantFile.SupplierRate_Not_Exist);



        if(record.getIssue()==true){

            //change in latest issue color box
            //and add the record in color acknowledgement

            ColorBox colorBox = colorBoxDao.getLatestColorBoxByItemIdWithIssuseFlag(record.getItemId(),true);
            if(colorBox==null)
                throw new Exception(ConstantFile.ColorBox_Not_Found);

            Double availableIssuedQty = colorBoxDao.getTotalQtyLeftByItemIdWithIssueFlag(record.getItemId(),true);

            //existingIssuedQty = existingIssuedQty==null ? record.getVisibleQty()
            if(record.getVisibleQty() > availableIssuedQty)
                colorBox.setQuantityLeft(colorBox.getQuantityLeft()+(record.getVisibleQty()-availableIssuedQty));
            else
                colorBox.setQuantityLeft(colorBox.getQuantityLeft()+(record.getVisibleQty()+availableIssuedQty));

            }
*/
        }
}
