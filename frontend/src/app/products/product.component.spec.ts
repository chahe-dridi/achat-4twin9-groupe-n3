import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductsComponent } from './products.component';
import { of } from 'rxjs';
import { Product } from '../shared/Model/Product';
import { ProductService } from '../shared/Service/Product.service';
import { FormsModule } from '@angular/forms'; // Ajoutez cette ligne

import {  fakeAsync, tick } from '@angular/core/testing';


describe('ProductsComponent', () => {
  let component: ProductsComponent;
  let fixture: ComponentFixture<ProductsComponent>;
  let productService: jasmine.SpyObj<ProductService>;

  const mockProduct: Product = {
    idProduit: 1,
    codeProduit: 'P001',
    libelleProduit: 'Product 1',
    prix: 100,
    dateCreation: '2023-01-01',
    dateDerniereModification: '2023-01-01'
  };

  const mockProducts: Product[] = [
    mockProduct,
    {
      idProduit: 2,
      codeProduit: 'P002',
      libelleProduit: 'Product 2',
      prix: 200,
      dateCreation: '2023-01-02',
      dateDerniereModification: '2023-01-02'
    }
  ];

  beforeEach(async () => {
    const productServiceSpy = jasmine.createSpyObj('ProductService', [
      'getAllProducts',
      'addProduct',
      'editProduct',
      'deleteProduct'
    ]);

    await TestBed.configureTestingModule({
      declarations: [ProductsComponent],
      providers: [
        { provide: ProductService, useValue: productServiceSpy }
      ]
    }).compileComponents();

    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;
    productService.getAllProducts.and.returnValue(of(mockProducts));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with products list', () => {
    expect(productService.getAllProducts).toHaveBeenCalled();
    expect(component.listProducts).toEqual(mockProducts);
  });

  it('should initialize product object', () => {
    expect(component.product).toEqual({
      idProduit: null,
      codeProduit: null,
      libelleProduit: null,
      prix: null,
      dateCreation: null,
      dateDerniereModification: null
    });
  });

  

  describe('addProduct', () => {
    it('should add a product and refresh list', fakeAsync(() => {
      const newProduct: Product = {
        idProduit: null,
        codeProduit: 'P003',
        libelleProduit: 'New Product',
        prix: 300,
        dateCreation: null,
        dateDerniereModification: null
      };

      productService.addProduct.and.returnValue(of(newProduct));
      productService.getAllProducts.and.returnValue(of([...mockProducts, newProduct]));

      component.addProduct(newProduct);
      tick();

      expect(productService.addProduct).toHaveBeenCalledWith(newProduct);
      expect(productService.getAllProducts).toHaveBeenCalled();
      expect(component.form).toBeFalse();
    }));
  });

  describe('editProduct', () => {
    it('should edit a product', () => {
      const updatedProduct: Product = {
        ...mockProduct,
        libelleProduit: 'Updated Product',
        prix: 150
      };

      productService.editProduct.and.returnValue(of(updatedProduct));

      component.editProduct(updatedProduct);

      expect(productService.editProduct).toHaveBeenCalledWith(updatedProduct);
    });
  });

  describe('deleteProduct', () => {
    it('should delete a product and refresh list', fakeAsync(() => {
      const productId = 1;
      productService.deleteProduct.and.returnValue(of({}));
      productService.getAllProducts.and.returnValue(of(mockProducts.filter(p => p.idProduit !== productId)));

      component.deleteProduct(productId);
      tick();

      expect(productService.deleteProduct).toHaveBeenCalledWith(productId);
      expect(productService.getAllProducts).toHaveBeenCalled();
    }));
  });
});