import { Component } from '@angular/core';
import { FileUploadService } from '../../services/file-upload.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent {
  selectedFile: File | null = null;
  validFormat: boolean = false;
  uploadSuccess: boolean = false;
  errorMessage: string = '';

  constructor(private fileUploadService: FileUploadService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile) {
      this.validateFile(this.selectedFile);
    }
  }

  validateFile(file: File) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const content = (e.target?.result as string).trim();
      const lines = content.split('\n');
      this.validFormat = lines.every(line => line.split(',').length === 6);
      if (!this.validFormat) {
        this.errorMessage = 'Formato incorrecto. Debe tener 6 columnas separadas por coma.';
      } else {
        this.errorMessage = '';
      }
    };
    reader.readAsText(file);
  }

  uploadFile() {
    if (this.selectedFile && this.validFormat) {
      this.fileUploadService.uploadFile(this.selectedFile).subscribe(response => {
        console.log('Archivo subido:', response);
        this.uploadSuccess = true;
      }, error => {
        console.error('Error al subir el archivo:', error);
        this.errorMessage = 'Error al subir el archivo';
      });
    } else {
      this.errorMessage = 'Seleccione un archivo válido';
    }
  }
}
