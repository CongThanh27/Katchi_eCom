package com.marwaeltayeb.souq.viewmodel;

import static com.marwaeltayeb.souq.net.LaptopDataSourceFactory.laptopDataSource;
import static com.marwaeltayeb.souq.net.ProductDataSourceFactory.productDataSource;
import static com.marwaeltayeb.souq.net.FlashSaleDataSourceFactory.flashSaleDataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.FlashSaleDataSourceFactory;
import com.marwaeltayeb.souq.net.LaptopDataSourceFactory;
import com.marwaeltayeb.souq.net.ProductDataSource;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;

public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;
    public LiveData<PagedList<Product>> flashsalePagedList;
    public LiveData<PagedList<Product>> laptopPagedList;

    // Get PagedList configuration
    private static final PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadMobiles(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);
//tạo một đối tượng LivePagedListBuilder với đối số là productDataSourceFactory và pagedListConfig.
// productDataSourceFactory là một đối tượng DataSource.Factory để cung cấp dữ liệu cho danh sách sản phẩm,
// được khởi tạo từ ProductDataSourceFactory. pagedListConfig là một đối tượng PagedList.Config, được sử dụng
// để cấu hình danh sách phân trang.
        // Build the paged list
        productPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }
    public void loadFlashSale( int userId){
        // Get our database source factory
        FlashSaleDataSourceFactory flashSaleDataSourceFactory = new FlashSaleDataSourceFactory(userId);
        // Build the paged list
        flashsalePagedList = (new LivePagedListBuilder<>(flashSaleDataSourceFactory, pagedListConfig)).build();
    }

    public void loadLaptops(String category, int userId){
        // Get our database source factory
        LaptopDataSourceFactory laptopDataSourceFactory = new LaptopDataSourceFactory(category,userId);

        // Build the paged list
        laptopPagedList = (new LivePagedListBuilder<>(laptopDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(productDataSource != null) productDataSource.invalidate();
        if(laptopDataSource!= null) laptopDataSource.invalidate();
        if(flashSaleDataSource!= null) flashSaleDataSource.invalidate();
    }
}
//Đoạn mã này tạo ra một lớp ProductViewModel trong gói com.marwaeltayeb.souq.viewmodel.
// Lớp này mở rộng lớp ViewModel của Android Architecture Components và cung cấp các phương thức để tải danh sách
// sản phẩm phân trang từ nguồn dữ liệu khác nhau.
//
//Lớp ProductViewModel có hai thuộc tính productPagedList và laptopPagedList kiểu LiveData<PagedList<Product>>.
// Đây là các danh sách sản phẩm phân trang sẽ được sử dụng để hiển thị các sản phẩm trên giao diện người dùng.
//
//Phương thức loadMobiles và loadLaptops được sử dụng để tải danh sách sản phẩm phân trang cho các danh mục tương ứng.
// Đầu vào cho phương thức này là một chuỗi category và một số nguyên userId. Để tải dữ liệu, phương thức này sử
// dụng các đối tượng ProductDataSourceFactory và LaptopDataSourceFactory để tạo ra đối tượng ProductDataSource và LaptopDataSource, tương ứng,
// từ đó xây dựng danh sách sản phẩm phân trang sử dụng LivePagedListBuilder và lưu trữ chúng vào productPagedList hoặc laptopPagedList.
//
//Phương thức invalidate được sử dụng để hủy bỏ các tài nguyên được sử dụng bởi đối tượng ProductDataSource và
// LaptopDataSource. Nó sẽ kiểm tra nếu các đối tượng productDataSource và laptopDataSource đã được khởi tạo thì nó sẽ gọi phương thức invalidate() để giải phóng các tài nguyên được sử dụng.