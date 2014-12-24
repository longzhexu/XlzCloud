package com.xlzcloud.ext;

import java.util.List;

import javax.sql.DataSource;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.xlzcloud.config.AppConfig;
import com.xlzcloud.ext.model.TableBind;
import com.xlzcloud.util.ClassScanner;
import com.xlzcloud.util.NameConverter;

public class ExActiveRecordPlugin extends ActiveRecordPlugin {
	
	private Logger log = Logger.getLogger(getClass());

	
	public ExActiveRecordPlugin(IDataSourceProvider dataSourceProvider) {
		super(dataSourceProvider);
		loadModel();
	}

	private void loadModel() {
		List<Class<? extends Model<?>>> models = new ClassScanner<Model<?>>(
				AppConfig.getModelPackage()).getClassList();
		for (Class<? extends Model> model : models) {
			TableBind table = model.getAnnotation(TableBind.class);
			String modelKey = null;
			if (table == null) {
				modelKey = AppConfig.getDbPerfix()
						+ "_"
						+ new NameConverter().hungarianToUnderline(model
								.getSimpleName());
			} else {
				modelKey = table.value();
			}
			addMapping(modelKey, (Class<? extends Model<?>>) model);
			log.debug("Model " + modelKey + "->" + model.getName());
		}
	}

}
