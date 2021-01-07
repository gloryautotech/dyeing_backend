package com.main.glory.servicesImpl;

import com.main.glory.Dao.color.ColorBoxDao;
import com.main.glory.Dao.color.ColorDataDao;
import com.main.glory.Dao.color.ColorMastDao;
import com.main.glory.Dao.SupplierDao;
import com.main.glory.model.color.ColorBox;
import com.main.glory.model.color.ColorData;
import com.main.glory.model.color.ColorMast;
import com.main.glory.model.color.request.IssueBoxRequest;
import com.main.glory.model.color.responsemodals.ColorMastDetails;
import com.main.glory.services.ColorServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorServicesInterface {

	@Autowired
	ColorMastDao colorMastDao;

	@Autowired
	ColorDataDao colorDataDao;

	@Autowired
	ColorBoxDao colorBoxDao;

	@Autowired
	SupplierDao supplierDao;

	@Override
	@Transactional
	public void addColor(ColorMast colorMast) {
			ColorMast colorMast1 = colorMastDao.save(colorMast);
			colorMast1.getColorDataList().forEach(e->{
				e.setControlId(colorMast1.getId());
			});
			colorDataDao.saveAll(colorMast1.getColorDataList());

			List<ColorBox> colorBoxes = new ArrayList<>();
			List<ColorData> cd = colorDataDao.findAllByControlId(colorMast1.getId());
			if(!cd.isEmpty()){
				cd.forEach(e -> {
					for (int i = 0; i < e.getNoOfBox(); i++) {
						ColorBox temp = new ColorBox();
						temp.setControlId(e.getId());
						temp.setIssued(false);
						temp.setFinished(false);
						temp.setQuantity(e.getQuantityPerBox());
						colorBoxes.add(temp);
					}
				});
				colorBoxDao.saveAll(colorBoxes);
			}
	}

	@Override
	public List<ColorMastDetails> getAll(String getBy,Long id)throws Exception {
		List<ColorMastDetails> colorMastDetails = new ArrayList<>();
		if(id == null){
			List<ColorMast> data = colorMastDao.getAllColorList();
			data.forEach(e -> {
				try{
					ColorMastDetails x = new ColorMastDetails(e);
					x.setSupplierName(supplierDao.findById(e.getSupplierId()).get().getSupplierName());
					colorMastDetails.add(x);
					/*e.getColorDataList().forEach(e1->{
						e1.setColorBoxes(colorBoxDao.findAllByControlId(e1.getId()));
					});*/
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}
		else if(getBy.equals("own")){
			List<ColorMast> data = colorMastDao.getAllByCreatedBy(id);
			data.forEach(e -> {
				try{
					ColorMastDetails x = new ColorMastDetails(e);
					x.setSupplierName(supplierDao.findById(e.getSupplierId()).get().getSupplierName());
					colorMastDetails.add(x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}
		else if(getBy.equals("group")){
			List<ColorMast> data = colorMastDao.getAllByUserHeadId(id);
			data.forEach(e -> {
				try{
					ColorMastDetails x = new ColorMastDetails(e);
					x.setSupplierName(supplierDao.findById(e.getSupplierId()).get().getSupplierName());
					colorMastDetails.add(x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}
		if(colorMastDetails.isEmpty())
			throw new Exception("no data found");

		return colorMastDetails;
	}

	public List<ColorMastDetails> getOne(Long id) {
		List<ColorMast> data = colorMastDao.getAllColorList();
		List<ColorMastDetails> colorMastDetails = new ArrayList<>();
			data.forEach(e -> {
				try{
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

		if(original.isEmpty()) {
			throw new Exception("No such color data present with id:" + colorMast.getId());
		}
		colorMastDao.save(colorMast);
		return true;
	}

	@Transactional
	public boolean deleteColorById(Long id) throws Exception{
		Optional<ColorMast> colorMast = colorMastDao.findById(id);

		// check if this is present in the database
		if(colorMast.isEmpty()){
			throw new Exception("color data does not exist with id:"+id);
		}

		colorMastDao.deleteById(id);

		return true;
	}

	public Optional<ColorMast> getColorById(Long id) {
		var getData = colorMastDao.findById(id);
		if(getData.isPresent())
			return getData;
		return null;
	}

	public List<ColorBox> getAllBox(Boolean issued) throws Exception{
		List<ColorBox> colorBoxes =colorBoxDao.findByIssued(issued);
		if(colorBoxes.isEmpty())
			throw new Exception("no data found");
		return colorBoxes;
	}

	public void issueBox(IssueBoxRequest issueBoxRequest) throws Exception{
		Optional<ColorBox> colorBox = colorBoxDao.findById(issueBoxRequest.getBoxId());
		if(colorBox.isEmpty()){
			throw new Exception("No such box found");
		}
		ColorBox colorBox1 = colorBox.get();
		colorBox1.setIssued(true);
		colorBoxDao.save(colorBox1);
	}
}
